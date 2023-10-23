package com.example.clinicsystem;

public class Material {

    private String materialId;
    private String materialName;
    private int quantity;

    public Material() {
        // Default constructor required for DataSnapshot.getValue(Material.class)
    }

    public Material(String materialId, String materialName, int quantity) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.quantity = quantity;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
