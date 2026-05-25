import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/inventory.css";
import closeimg from "./icon/close.png";

function Inventory() {

  // ---------------- STATE ----------------

  const [inventory, setInventory] = useState([]);

  const [categories, setCategories] = useState([]);
  const [suppliers, setSuppliers] = useState([]);

  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedSupplier, setSelectedSupplier] = useState("");
  const [selectedStockFilter, setSelectedStockFilter] = useState("");

  const [error, setError] = useState("");

  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");

  const [showPopup, setShowPopup] = useState(false);
  const [showEmailPopup, setShowEmailPopup] = useState(false);

  const [emailData, setEmailData] = useState({
    email: "",
    type: "csv",
  });


  const [page, setPage] = useState(0);
  const [size] = useState(2);

  const [totalPages, setTotalPages] = useState(0);

  const [sortBy, setSortBy] = useState("");
  const [ascending, setAscending] = useState(true);


  useEffect(() => {
    fetchInventory(page);

    fetchCategories();

    fetchSuppliers();
  }, [page, sortBy, ascending]);


  const fetchInventory = async (pageNumber = 0) => {
    try {
      const res = await axios.get(
        "http://localhost:8080/inventory/alldata/data",
        {
          params: {
            page: pageNumber,
            size: size,
            sortBy: sortBy,
            ascending: ascending,
          },
        },
      );

      setInventory(res.data.content || []);
      setTotalPages(res.data.totalPages || 0);
    } catch (err) {
      console.log(err);

      setError("Failed to load inventory data");
    }
  };



  // -- FETCH CATEGORIES ---
  const fetchCategories = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/inventory/alldata/categories",
      );

      setCategories(res.data);
    } catch (err) {
      console.log(err);
    }
  };



  // ---  FETCH SUPPLIERS ---
  const fetchSuppliers = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/inventory/alldata/suppliers",
      );

      setSuppliers(res.data);
    } catch (err) {
      console.log(err);
    }
  };



  const handleSearch = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/inventory/alldata/filter",
        {
          params: {
            categoryName: selectedCategory || null,
            supplierName: selectedSupplier || null,
            stockFilter: selectedStockFilter || null,
          },
        },
      );

      setInventory(res.data);

      setTotalPages(1);

      setPage(0);
    } catch (err) {
      console.log(err);

      alert("Failed to filter data");
    }
  };



  const handleReset = () => {
    setSelectedCategory("");

    setSelectedSupplier("");

    setSelectedStockFilter("");

    setPage(0);

    fetchInventory(0);
  };



  // --- DOWNLOAD ---
  const handleDownload = (type) => {
    if (!fromDate || !toDate) {
      alert("Please select dates");

      return;
    }

    window.open(
      `http://localhost:8080/inventory/alldata/export/${type}?fromDate=${fromDate}&toDate=${toDate}`,
      "_blank",
    );

    setShowPopup(false);
  };




  // --- EMAIL ---
  const sendEmail = async () => {
    if (!emailData.email) {
      alert("Please enter email");

      return;
    }

    try {
      const res = await axios.post(
        "http://localhost:8080/inventory/alldata/email-report",
        {
          email: emailData.email,
          type: emailData.type,
          fromDate,
          toDate,
        },
      );

      alert(res.data);

      setShowEmailPopup(false);

      setShowPopup(false);
    } catch (err) {
      console.log(err);

      alert("Failed to send email");
    }
  };



  // -- PAGINATION --
  const handlePrevious = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  const handleNext = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
    }
  };

  if (error) return <h2>{error}</h2>;




  return (


    <div className="inventory-container">

      <div className="top-controls">


        <select
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
        >
          <option value="">Select Category</option>

          {categories.map((c) => (
            <option key={c.categoryId} value={c.categoryName}>
              {c.categoryName}
            </option>
          ))}
        </select>

        <select
          value={selectedSupplier}
          onChange={(e) => setSelectedSupplier(e.target.value)}
        >
          <option value="">Select Supplier</option>

          {suppliers.map((s) => (
            <option key={s.supplierId} value={s.supplierName}>
              {s.supplierName}
            </option>
          ))}
        </select>

        <select
          value={selectedStockFilter}
          onChange={(e) => setSelectedStockFilter(e.target.value)}
        >
          <option value="">Stock Filter</option>

          <option value="lt10">Less than 10</option>

          <option value="lt50">Less than 50</option>

          <option value="lt100">Less than 100</option>

          <option value="gt100">Greater than 100</option>
        </select>

        <button className="download-btn" onClick={handleSearch}>
          Search
        </button>

        <button className="download-btn" onClick={handleReset}>
          Reset
        </button>

        <button className="download-btn" onClick={() => setShowPopup(true)}>
          Download
        </button>
      </div>

      <div className="sort-controls">
        <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
          <option value="">Sort By</option>
          <option value="id">ID</option>
          <option value="currentStock">Current Stock</option>
          <option value="stockIn">Stock In</option>
          <option value="stockOut">Stock Out</option>
        </select>

        <button className="download-btn" onClick={() => setAscending(!ascending)}>
          {ascending ? "Ascending ↑" : "Descending ↓"}
        </button>
      </div>


      <div className="inventory-content">
        <h2>Inventory Details</h2>
      </div>

      <table className="inventory-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Stock In</th>
            <th>Stock Out</th>
            <th>Current Stock</th>
            <th>Last Updated</th>
          </tr>
        </thead>

        <tbody>
          {inventory && inventory.length > 0 ? (
            inventory.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>

                <td>{item.productName}</td>

                <td>{item.stockIn}</td>

                <td>{item.stockOut}</td>

                <td>{item.currentStock}</td>

                <td>
                  {item.lastUpdated
                    ? new Date(item.lastUpdated).toLocaleString()
                    : "N/A"}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6" style={{ textAlign: "center" }}>
                No Data Available
              </td>
            </tr>
          )}
        </tbody>
      </table>

      <div className="pagination-container">
        <button
          className="pagination-btn"
          onClick={handlePrevious}
          disabled={page === 0}
        >
          Previous
        </button>

        <span className="page-info">
          Page {page + 1} of {totalPages}
        </span>

        <button
          className="pagination-btn"
          onClick={handleNext}
          disabled={page >= totalPages - 1}
        >
          Next
        </button>
      </div>


      {showPopup && (
        <div className="download-overlay">
          <div className="download-modal">
            
            <div className="download-header">
              <h2>Download Report</h2>

              <img
                className="download-close"
                src={closeimg}
                alt="Close"
                onClick={() => setShowPopup(false)}
              />
            </div>

            <div className="download-body">

              <div className="date-section">
                <div className="date-field">
                  <label>From Date</label>

                  <input
                    className="download-date-input"
                    type="date"
                    value={fromDate}
                    onChange={(e) => setFromDate(e.target.value)}
                  />
                </div>

                <div className="date-field">
                  <label>To Date</label>

                  <input
                    className="download-date-input"
                    type="date"
                    value={toDate}
                    onChange={(e) => setToDate(e.target.value)}
                  />
                </div>
              </div>

              <div className="download-buttons">
                <button onClick={() => handleDownload("csv")}>CSV</button>

                <button onClick={() => handleDownload("pdf")}>PDF</button>

                <button onClick={() => handleDownload("json")}>JSON</button>

                <button>IMG</button>

                <button
                  className="email-report-btn"
                  onClick={() => {
                    if (!fromDate || !toDate) {
                      alert("Please select dates");

                      return;
                    }

                    setShowEmailPopup(true);
                  }}
                >
                  Email Report
                </button>
              </div>
            </div>
          </div>
        </div>
      )}


      {showEmailPopup && (

        <div className="email-popup-overlay">
          <div className="email-popup-box">

            <div className="email-popup-header">
              <h2>Email Report</h2>

              <img
                className="email-popup-close"
                src={closeimg}
                alt="Close"
                onClick={() => setShowEmailPopup(false)}
              />
            </div>

            <div className="email-popup-body">
              <div className="email-field">
                <label>Email Address</label>

                <input
                  className="email-input"
                  type="email"
                  placeholder="Enter your email"
                  value={emailData.email}
                  onChange={(e) =>
                    setEmailData({
                      ...emailData,
                      email: e.target.value,
                    })
                  }
                />
              </div>

              <div className="email-field">
                <label>Select File Type</label>

                <select
                  className="email-select"
                  value={emailData.type}
                  onChange={(e) =>
                    setEmailData({
                      ...emailData,
                      type: e.target.value,
                    })
                  }
                >
                  <option value="csv">CSV</option>

                  <option value="pdf">PDF</option>

                  <option value="json">JSON</option>
                </select>
              </div>

              <div className="email-date-info">
                <p>Report Date Range:</p>

                <span>
                  {fromDate} → {toDate}
                </span>
              </div>

              <button className="send-email-btn" onClick={sendEmail}>
                Send Report
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Inventory;
