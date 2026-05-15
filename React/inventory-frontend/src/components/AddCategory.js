import React, { useState } from "react";
import axios from "axios";
import "./css/category.css"; 

function AddCategory() {

  const [category, setCategory] = useState({
    categoryName: "",
    description: ""
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setCategory({
      ...category,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8080/inventory/categories",
        category
      );

      alert("Category Added Successfully");

      // reset form
      setCategory({
        categoryName: "",
        description: ""
      });

      setErrors({});

    } catch (error) {

      if (error.response && error.response.data) {

        const data = error.response.data;

        // DTO field errors
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
      <h2>Add Category</h2>

      <form onSubmit={handleSubmit}>

        <div className="input-group">
          <label>Category Name</label>
          <input
            type="text"
            name="categoryName"
            value={category.categoryName}
            onChange={handleChange}
          />
          {errors.categoryName && <span className="error">{errors.categoryName}</span>}
        </div>

        <div className="input-group">
          <label>Description</label>
          <textarea
            name="description"
            value={category.description}
            onChange={handleChange}
          />
          {errors.description && <span className="error">{errors.description}</span>}

        </div>

        <div className="cat-button-row">
          <button  type="submit">Save Purchase</button>
          <button  type="button">Back to Dashboard</button>
        </div>

      </form>

    </div>
  );
}

export default AddCategory;