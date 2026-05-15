import React, { useState } from "react";
import axios from "axios";
import "./css/supplier.css";

function AddSupplier() {
  const [supplier, setSupplier] = useState({
    supplierName: "",
    contactPerson: "",
    phone: "",
    email: "",
    gst_no: "",
    state: "",
    city: "",
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setSupplier({
      ...supplier,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/inventory/suppliers", supplier);

      alert("Supplier Added Successfully");

      // reset form
      setSupplier({
        supplierName: "",
        contactPerson: "",
        phone: "",
        email: "",
        gst_no: "",
        state: "",
        city: "",
      });

      setErrors({});
    } catch (error) {
      if (error.response && error.response.data) {
        const data = error.response.data;

        // Field validation errors (DTO)
        if (typeof data === "object" && !data.message) {
          setErrors(data);
        }

        // Custom validator errors
        else if (data.message) {
          alert(data.message);
        }
      } else {
        alert("Something went wrong");
      }
    }
  };

  return (
    <div className="container-box">
      <h1>Inventory Management System</h1>
      <h2>Add Supplier</h2>

      <form onSubmit={handleSubmit}>


        <div className="input-group">
          <label>Supplier Name</label>
          <input
            type="text"
            name="supplierName"
            value={supplier.supplierName}
            onChange={handleChange}
          />
          {errors.supplierName && (
            <p className="error">{errors.supplierName}</p>
          )}
        </div>


        <div className="input-group">
          <label>Contact Person</label>
          <input
            type="text"
            name="contactPerson"
            value={supplier.contactPerson}
            onChange={handleChange}
          />
          {errors.contactPerson && (
            <p className="error">{errors.contactPerson}</p>
          )}
        </div>

         <div className="input-group">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={supplier.email}
            onChange={handleChange}
          />
          {errors.email && <p className="error">{errors.email}</p>}
        </div>

        <div className="div-row-1"> 
          <div className="input-group">
            <label>Phone</label>
            <input 
              className="input-phone"
              type="text"
              name="phone"
              value={supplier.phone}
              onChange={handleChange}
            />
            {errors.phone && <p className="error">{errors.phone}</p>}
          </div>

          <div className="input-group">
           <label>GST No.</label>
            <input
              className="input-phone"
              type="text"
              name="gst_no"
              value={supplier.gst_no}
              onChange={handleChange}
            />
            {errors.gst_no && <p className="error">{errors.gst_no}</p>}
        </div>
        </div>
        


        <div className="div-row-2">
          <div className="input-group">
            <label>State</label>
            <input
              className="input-state"
              type="text"
              name="state"
              value={supplier.state}
              onChange={handleChange}
            />
            {errors.state && <p className="error">{errors.state}</p>}
          </div>



          <div className="input-group">
            <label>City</label>
            <input
              className="input-city"
              type="text"
              name="city"
              value={supplier.city}
              onChange={handleChange}
            />
            {errors.city && <p className="error">{errors.city}</p>}
          </div>
        </div>

        <div className="sup-button-row">
          <button type="submit">Save Purchase</button>
          <button type="button">Back to Dashboard</button>
        </div>    
        
        </form>
    </div>
  );
}

export default AddSupplier;
