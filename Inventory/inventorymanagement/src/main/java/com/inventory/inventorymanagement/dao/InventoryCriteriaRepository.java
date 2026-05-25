package com.inventory.inventorymanagement.dao;

import com.inventory.inventorymanagement.entity.Category;
import com.inventory.inventorymanagement.entity.Inventory;
import com.inventory.inventorymanagement.entity.Product;
import com.inventory.inventorymanagement.entity.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Inventory> filterInventory(String categoryName,
                                           String supplierName,
                                           String stockFilter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Inventory> criteriaQuery = criteriaBuilder.createQuery(Inventory.class);

        Root<Inventory> inventory = criteriaQuery.from(Inventory.class);

        List<Predicate> predicates = new ArrayList<>();

        Join<Inventory, Product> product = inventory.join("product");
        Join<Product, Category> category = product.join("category");
        Join<Product, Supplier> supplier = product.join("suppliers");

        if (categoryName != null && !categoryName.isEmpty()) {
            predicates.add(
                    criteriaBuilder.equal(category.get("categoryName"), categoryName)
            );
        }

        if (supplierName != null && !supplierName.isEmpty()) {
            predicates.add(
                    criteriaBuilder.equal(supplier.get("supplierName"), supplierName)
            );
        }

        if (stockFilter != null && !stockFilter.isEmpty()) {

            switch (stockFilter) {

                case "lt10":
                    predicates.add(criteriaBuilder.lt(inventory.get("currentStock"), 10));
                    break;

                case "lt50":
                    predicates.add(criteriaBuilder.lt(inventory.get("currentStock"), 50));
                    break;

                case "lt100":
                    predicates.add(criteriaBuilder.lt(inventory.get("currentStock"), 100));
                    break;

                case "gt100":
                    predicates.add(criteriaBuilder.gt(inventory.get("currentStock"), 100));
                    break;
            }
        }

        criteriaQuery.select(inventory).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}