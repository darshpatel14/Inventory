import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/product.css";

function AddProduct() {
  const [product, setProduct] = useState({
    productName: "",
    productDescription: "",
    price: "",
    stock: "",
    categoryId: "",
    supplierIds: [],
  });

  const [categories, setCategories] = useState([]);
  const [suppliers, setSuppliers] = useState([]);
  const [errors, setErrors] = useState({});

  // Load categories & suppliers
  useEffect(() => {
    fetchCategories();
    fetchSuppliers();
  }, []);

  const fetchCategories = async () => {
    try {
      const res = await axios.get("http://localhost:8080/inventory/categories");
      setCategories(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchSuppliers = async () => {
    try {
      const res = await axios.get("http://localhost:8080/inventory/suppliers");
      setSuppliers(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Handle input
  const handleChange = (e) => {
  const { name, value } = e.target;

  setProduct({
    ...product,
    [name]:
      name === "categoryId" || name === "price" || name === "stock"
        ? Number(value)
        : value,
  });
};


  // Handle multi-select (suppliers)
 const handleSupplierChange = (e) => {
  const selected = Array.from(
    e.target.selectedOptions,
    (option) => Number(option.value)   
  );

  setProduct({
    ...product,
    supplierIds: selected,
  });
};

  // Submit
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/inventory/products", product);

      alert("Product Added Successfully");

      setProduct({
        productName: "",
        productDescription: "",
        price: "",
        stock: "",
        categoryId: "",
        supplierIds: [],
      });

      setErrors({});
    } catch (error) {
      if (error.response && error.response.data) {
        const data = error.response.data;

        // DTO validation errors
        if (typeof data === "object" && !data.message) {
          setErrors(data);
        }

        // Custom exception
        else if (data.message) {
          alert(data.message);
        }
      } else {
        alert("Something went wrong");
      }
    }
  };

  return (
    <div className="product-container">
      <h1>Inventory Management System</h1>
      <h2>Add Product</h2>

      <form onSubmit={handleSubmit}>
        <div className="product-input-group">
          <label>Product Name</label>
          <input
            type="text"
            name="productName"
            value={product.productName}
            onChange={handleChange}
          />
          {errors.productName && <p className="error">{errors.productName}</p>}
        </div>

        <div className="product-input-group">
          <label>Product Description</label>
          <textarea
            name="productDescription"
            value={product.productDescription}
            onChange={handleChange}
          />
          {errors.productDescription && (
            <p className="error">{errors.productDescription}</p>
          )}
        </div>



        <div className="product-div-row-1">
          <div className="product-input-group">
            <label>Category</label>
            <select
              name="categoryId"
              value={product.categoryId}
              onChange={handleChange}
            >
              <option value="">Select Category</option>

              {categories.map((cat) => (
                <option key={cat.categoryId} value={cat.categoryId}>
  {cat.categoryName}
</option>
              ))}
            </select>
            {errors.categoryId && <p className="error">{errors.categoryId}</p>}
          </div>

          <div className="product-input-group">
            <label>Suppliers</label>
            <select
              multiple
              value={product.supplierIds}
              onChange={handleSupplierChange}
            >
              {suppliers.map((sup) => (
                <option key={sup.supplierId} value={sup.supplierId}>
  {sup.supplierName}
</option>
              ))}
            </select>
          </div>
        </div>

        <div className="product-div-row-2">
          <div className="product-input-group">
            <label>Price</label>
            <input
              type="number"
              name="price"
              value={product.price}
              onChange={handleChange}
            />
            {errors.price && <p className="error">{errors.price}</p>}
          </div>

          <div className="product-input-group">
            <label>Stock</label>
            <input
              type="number"
              name="stock"
              value={product.stock}
              onChange={handleChange}
            />
            {errors.stock && <p className="error">{errors.stock}</p>}
          </div>
        </div>

        <div className="pro-button-row">
          <button type="submit">Save Purchase</button>
          <button type="button">Back to Dashboard</button>
        </div>   

      
      </form>
    </div>
  );
}

export default AddProduct;
