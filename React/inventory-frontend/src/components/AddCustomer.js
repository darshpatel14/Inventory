import React, { useState } from "react";
import axios from "axios";
import './css/customer.css';


function AddCustomer() {

  const [customer, setCustomer] = useState({
    customerName: "",
    phone: "",
    email: "",
    state: "",
    city: ""
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setCustomer({
      ...customer,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/inventory/customers",
        customer
      );

      alert("Customer Added Successfully");

      setCustomer({
        customerName: "",
        phone: "",
        email: "",
        state: "",
        city: ""
      });

      setErrors({}); // clear errors

    } 
    catch (error) 
    {

    if (error.response && error.response.data) {

        const data = error.response.data;

        // If field errors
        if (typeof data === "object" && !data.message) {
        setErrors(data);
        }

        // If custom exception
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
      <h2>Add Customer</h2>

      <form onSubmit={handleSubmit}>

        <div className="input-group">
          <label>Customer Name:</label>
          <input
            type="text"
            name="customerName"
            value={customer.customerName}
            onChange={handleChange}
          />
          {errors.customerName && <span className="error">{errors.customerName}</span>}
        </div>


        <div className="input-group">
          <label>Contact:</label>
          <input
            type="text"
            name="phone"
            value={customer.phone}
            onChange={handleChange}
          />
          {errors.phone && <span className="error">{errors.phone}</span>}
        </div>


        <div className="input-group">
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={customer.email}
            onChange={handleChange}
          />
          {errors.email && <span className="error">{errors.email}</span>}
        </div>

        <div className="div-row-1">
           <div className="input-group">
          <label>State:</label>
          <input
            className="input-state"
            type="text"
            name="state"
            value={customer.state}
            onChange={handleChange}
          />
          {errors.state && <span className="error">{errors.state}</span>}
        </div>


        <div className="input-group">
          <label>City:</label>
          <input
            className="input-city"
            type="text"
            name="city"
            value={customer.city}
            onChange={handleChange}
          />
          {errors.city && <span className="error">{errors.city}</span>}
        </div>


        </div>

       
      <div className="cus-button-row">
          <button type="submit">Save Purchase</button>
          <button type="button">Back to Dashboard</button>
        </div>
      </form>
    </div>
  );
}

export default AddCustomer;