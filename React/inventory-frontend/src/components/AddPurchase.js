import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/purchase.css";

function AddPurchase() {

  const [purchase, setPurchase] = useState({
    supplierId: "",
    purchaseDate: "",
    purchaseStatus: "",
    purchaseItems: [
      { productId: "", quantity: "", unitCost: "" }
    ]
  });


  // SUCCESS MESSAGE
    const [successMessage, setSuccessMessage] = useState("");
  
    // ERROR POPUP
    const [showPopup, setShowPopup] = useState(false);
  
    const [popupMessage, setPopupMessage] = useState("");

  const [suppliers, setSuppliers] = useState([]);
  const [products, setProducts] = useState([]);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    fetchSuppliers();
    fetchProducts();
  }, []);

  const fetchSuppliers = async () => {
    const res = await axios.get("http://localhost:8080/inventory/suppliers");
    setSuppliers(res.data);
  };

  const fetchProducts = async () => {
    const res = await axios.get("http://localhost:8080/inventory/products");
    setProducts(res.data);
  };

  const handleChange = (e) => {
  const { name, value } = e.target;

  setPurchase({
    ...purchase,
    [name]:
      name === "supplierId"
        ? Number(value)
        : value
  });
};

 const handleItemChange = (index, e) => {
  const { name, value } = e.target;

  const updatedItems = [...purchase.purchaseItems];

  updatedItems[index][name] =
    name === "productId" || name === "quantity" || name === "unitCost"
      ? value === "" ? "" : Number(value)
      : value;

  setPurchase({
    ...purchase,
    purchaseItems: updatedItems
  });
};

  const addRow = () => {
    setPurchase({
      ...purchase,
      purchaseItems: [
        ...purchase.purchaseItems,
        { productId: "", quantity: "", unitCost: "" }
      ]
    });
  };

  const removeRow = (index) => {
    const updatedItems = purchase.purchaseItems.filter((_, i) => i !== index);

    setPurchase({
      ...purchase,
      purchaseItems: updatedItems
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    for (let item of purchase.purchaseItems) {
    if (!item.productId || !item.quantity || !item.unitCost) {
      alert("Please fill all purchase item fields");
      return;
    }
  }

    try {
      await axios.post(
        "http://localhost:8080/inventory/purchase",
        purchase
      );

      setSuccessMessage("Sales Added Successfully");

      setPurchase({
        supplierId: "",
        purchaseDate: "",
        purchaseStatus: "",
        purchaseItems: [{ productId: "", quantity: "", unitCost: "" }]
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
    <div className="container1">

      <h1>Inventory Management System</h1>
      <h2>Add Purchase</h2>

      <form onSubmit={handleSubmit}>

        {/*  Top Row */}
        <div className="form-row">

          <div className="input-group-1">
            <label>Supplier</label>
            <select
              className="supplier-input"
              name="supplierId"
              value={purchase.supplierId}
              onChange={handleChange}
            >
              <option value="">Select Supplier</option>
              {suppliers.map(sup => (
  <option key={sup.supplierId} value={sup.supplierId}>
    {sup.supplierName}
  </option>
))}
            </select>
            {errors.supplierId && <p className="error">{errors.supplierId}</p>}
          </div>

          <div className="input-group-1">
            <label>Date</label>
            <input
              className="date-input"
              type="date"
              name="purchaseDate"
              value={purchase.purchaseDate}
              onChange={handleChange}
            />
            {errors.purchaseDate && <p className="error">{errors.purchaseDate}</p>}
          </div>

          <div className="input-group-1">
            <label>Status</label>
            <input
              className="status-input"
              type="text"
              name="purchaseStatus"
              value={purchase.purchaseStatus}
              onChange={handleChange}
            />
            {errors.purchaseStatus && <p className="error">{errors.purchaseStatus}</p>}
          </div>

        </div>

        {/* Add Button */}
       <div className="purchase-action-row">
          <button className="purchase-additem-btn" type="button" onClick={addRow}>
            Add Item
          </button>
        </div>

        <h3>Purchase Items</h3>

        {/*  Scrollable Table */}
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Product</th>
                <th>Quantity</th>
                <th>Unit Cost</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {purchase.purchaseItems.map((item, index) => (
                <tr key={index}>

                  <td>
                    <select 
                      // className="product-list"
                      name="productId"
                      value={item.productId}
                      onChange={(e) => handleItemChange(index, e)}
                    >
                      <option value="">Select</option>
                      {products.map(p => (
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
                      name="unitCost"
                      value={item.unitCost}
                      onChange={(e) => handleItemChange(index, e)}
                    />
                  </td>

                  <td className="td-button">
                   <button  className="update-btn" type="button"> Update </button>
                   <button className="remove-btn" type="button" onClick={() => removeRow(index)}> Remove </button>
                    
                  </td>

                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="pur-button-row">
               {successMessage && (
                <div className="success-msg">
                    {successMessage}
                </div>
            )}  
          <button className="submit-btn" type="submit">Save Purchase</button>
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

export default AddPurchase;