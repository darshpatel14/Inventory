import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/sales.css";

function AddSales() {
  const [sales, setSales] = useState({
    customerID: "",
    
    salesDate: "",
    modeOfPayment: "",
    userId: "",
    salesItems: [{ productId: "", quantity: "", unitPrice: "" }],
  });

  // SUCCESS MESSAGE
  const [successMessage, setSuccessMessage] = useState("");

  // ERROR POPUP
  const [showPopup, setShowPopup] = useState(false);

  const [popupMessage, setPopupMessage] = useState("");

  const [customers, setCustomers] = useState([]);
  const [products, setProducts] = useState([]);
  const [users, setUsers] = useState([]);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    fetchCustomers();
    fetchProducts();
    fetchUsers();
  }, []);

  const fetchCustomers = async () => {
    const res = await axios.get("http://localhost:8080/inventory/customers");
    setCustomers(res.data);
  };

  const fetchProducts = async () => {
    const res = await axios.get("http://localhost:8080/inventory/products");
    setProducts(res.data);
  };

  const fetchUsers = async () => {
    const res = await axios.get("http://localhost:8080/inventory/users");
    setUsers(res.data);
  };

  const handleChange = (e) => {
    setSales({
      ...sales,
      [e.target.name]: e.target.value,
    });
  };

  const handleItemChange = (index, e) => {
    const updatedItems = [...sales.salesItems];
    updatedItems[index][e.target.name] = e.target.value;

    setSales({
      ...sales,
      salesItems: updatedItems,
    });
  };

  const addRow = () => {
    setSales({
      ...sales,
      salesItems: [
        ...sales.salesItems,
        { productId: "", quantity: "", unitPrice: "" },
      ],
    });
  };

  const removeRow = (index) => {
    const updatedItems = sales.salesItems.filter((_, i) => i !== index);

    setSales({
      ...sales,
      salesItems: updatedItems,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/inventory/sales", sales);

      
      setSuccessMessage("Sales Added Successfully");

      setSales({
        customerID: "",
        salesDate: "",
        modeOfPayment: "",
        userId: "",
        salesItems: [{ productId: "", quantity: "", unitPrice: "" }],
      });

      setErrors({});
    } catch (error) {
      setPopupMessage(
                error.response?.data?.message ||
                "Something went wrong"
            );

            setShowPopup(true);
    }
  };

  return (
    <div className="sales-container">
      <h1>Inventory Management System</h1>
      <h2>Add Sales</h2>

     

      <form onSubmit={handleSubmit}>

        <div className="sales-form-row">
          <div className="sales-input-group-11">
            <label>Customer</label>
            <select
              name="customerID"
              value={sales.customerID}
              onChange={handleChange}
            >
              <option value="">Select Customer</option>
              {customers.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.customerName}
                </option>
              ))}
            </select>
            {errors.customerID && <p className="error">{errors.customerID}</p>}
          </div>

          <div className="sales-input-group-11">
            <label>Date</label>
            <input
              type="date"
              name="salesDate"
              value={sales.salesDate}
              onChange={handleChange}
            />
            {errors.salesDate && <p className="error">{errors.salesDate}</p>}
          </div>

          <div className="sales-input-group-11">
            <label>Mode Of Payment</label>
            <input
              type="text"
              name="modeOfPayment"
              value={sales.modeOfPayment}
              onChange={handleChange}
            />
            {errors.modeOfPayment && (
              <p className="error">{errors.modeOfPayment}</p>
            )}
          </div>

          <div className="sales-input-group-11">
            <label>Created By</label>
            <select name="userId" value={sales.userId} onChange={handleChange}>
              <option value="">Select User</option>
              {users.map((u) => (
                <option key={u.userid} value={u.userid}>
                  {u.username}
                </option>
              ))}
            </select>
            {errors.userId && <p className="error">{errors.userId}</p>}
          </div>
        </div>


        {/* ADD ITEM BUTTON ROW */}
        <div className="sales-action-row">
          <button className="sales-additem-btn" type="button" onClick={addRow}>
            Add Item
          </button>
        </div>

        <h3>Sales Items</h3>

        {/* TABLE */}
        <div className="sales-table-container">
          <table>
            <thead>
              <tr>
                <th>Product</th>
                <th>Quantity</th>
                <th>Unit Price</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {sales.salesItems.map((item, index) => (
                <tr key={index}>
                  <td>
                    <select
                      name="productId"
                      value={item.productId}
                      onChange={(e) => handleItemChange(index, e)}
                    >
                      <option value="">Select</option>
                      {products.map((p) => (
                        <option key={p.productId} value={p.productId}>
                          {p.productName}
                        </option>
                      ))}
                    </select>
                  </td>

                  <td>
                    <input
                      type="number"
                      name="quantity"
                      value={item.quantity}
                      onChange={(e) => handleItemChange(index, e)}
                    />
                  </td>

                  <td>
                    <input
                      type="number"
                      name="unitPrice"
                      value={item.unitPrice}
                      onChange={(e) => handleItemChange(index, e)}
                    />
                  </td>

                  <td className="sales-td-button">
                    <button className="sales-update-btn" type="button">
                      Update
                    </button>
                    <button
                      className="sales-remove-btn"
                      type="button"
                      onClick={() => removeRow(index)}
                    >
                      Remove
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* BUTTONS */}
        <div className="sales-button-row">
           {successMessage && (
                <div className="success-msg">
                    {successMessage}
                </div>
            )}  
          <button className="submit-btn" type="submit">Save Sales</button>
          <button className="back-btn" type="button">Back to Dashboard</button>
        </div>
      </form>


      {
                showPopup && (

                    <div className="popup-overlay">

                        <div className="popup-box">

                            <h3>Error</h3>
                            <br></br>
                            <p>{popupMessage}</p>

                            <button
                                onClick={() => setShowPopup(false)}
                            >
                                OK
                            </button>

                        </div>

                    </div>
                )
            }


    </div>
  );
}

export default AddSales;
