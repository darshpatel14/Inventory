import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/dashboard.css";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const [data, setData] = useState({
    totalProducts: 0,
    totalCategories: 0,
    totalSuppliers: 0,
    totalPurchase: 0,
    totalSales: 0
  });

  const navigate = useNavigate();

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/inventory/dashboard"
      );   
      
      console.log("API Response:", response.data);

      setData(response.data);
    } catch (error) {
      console.error(error);
      console.log(error);
      alert("Error loading dashboard");
    }
  };

  return (
    <div className="main-container">

      {/* Sidebar */}
      <div className="navigation-bar">
        <h1 className="heading">Inventory System</h1>

        <button className="btn" onClick={() => navigate("/add-category")}>Add Category</button>
        <button className="btn" onClick={() => navigate("/add-customer")}>Add Customer</button>
        <button className="btn" onClick={() => navigate("/add-supplier")}>Add Supplier</button>
        <button className="btn" onClick={() => navigate("/add-product")}>Add Product</button>
        <button className="btn" onClick={() => navigate("/add-purchase")}>Add Purchase</button>
        <button className="btn" onClick={() => navigate("/add-sales")}>Add Sales</button>
        <button className="btn" onClick={() => navigate("/add-user")}>Add User</button>
      </div>

      {/* Main Content */}
      <div className="main-content">

        <h2 className="dashboard-heading">Welcome to Dashboard</h2>

        <div className="dashboard-header-content">

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Products</label>
            <label className="header-box-data">{data.totalProducts}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Category</label>
            <label className="header-box-data">{data.totalCategories}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Supplier</label>
            <label className="header-box-data">{data.totalSuppliers}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Purchase</label>
            <label className="header-box-data">{data.totalPurchase}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Sales</label>
            <label className="header-box-data">{data.totalSales}</label>
          </div>

        </div>

        <button className="inv-btn" onClick={() => navigate("/view-Inventory")}>View Inventory</button>


      </div>

    </div>
  );
}

export default Dashboard;