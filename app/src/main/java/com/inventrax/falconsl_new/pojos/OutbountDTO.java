package com.inventrax.falconsl_new.pojos;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by karthik.m on 07/04/2018.
 */

public class OutbountDTO {

    @SerializedName("OutboundID")
    private String outboundID;

    @SerializedName("PickRefNo")
    private List<String> pickRefNo;

    @SerializedName("MRP")
    private String MRP;

    @SerializedName("MOP")
    private String MOP;

    @SerializedName("SKU")
    private String SKU;

    @SerializedName("SerialNo")
    private String serialNo;

    @SerializedName("MfgDate")
    private String MfgDate;

    @SerializedName("Location")
    private String location;

    @SerializedName("LoadList")
    private List<LoadDTO> LoadList;

    @SerializedName("PalletNo")
    private String palletNo;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("IsMaterialDamaged")
    private Boolean isMaterialDamaged;

    @SerializedName("IsMaterialNotFound")
    private Boolean isMaterialNotFound;

    @SerializedName("Result")
    private String result;

    @SerializedName("RequiredQty")
    private String requiredQty;

    @SerializedName("PickedQty")
    private String pickedQty;

    @SerializedName("SelectedPickRefNumber")
    private String SelectedPickRefNumber;

    @SerializedName("SelectedLoadSheetNumber")
    private String SelectedLoadSheetNumber;

    @SerializedName("AllowNestedInventoryDispatch")
    private Boolean AllowNestedInventoryDispatch;

    @SerializedName("AllowDispatchOfOLDMRP")
    private Boolean AllowDispatchOfOLDMRP;

    @SerializedName("AllowCrossDocking")
    private Boolean AllowCrossDocking;

    @SerializedName("StrictComplianceToPicking")
    private Boolean StrictComplianceToPicking;

    @SerializedName("AutoReconsileInventoryOnSkip")
    private Boolean AutoReconsileInventoryOnSkip;

    @SerializedName("DockNumber")
    private String dockNumber;

    @SerializedName("SuggestionID")
    private String SuggestionID;

    @SerializedName("RevertQty")
    private String RevertQty;

    @SerializedName("CustomerCode")
    private String CustomerCode;

    @SerializedName("MaterialMasterId")
    private String MaterialMasterId;

    @SerializedName("OBDNo")
    private String oBDNo;

    @SerializedName("BatchNo")
    private String batchNo;

    @SerializedName("AssignedQuantity")
    private String assignedQuantity;

    @SerializedName("PendingQty")
    private String pendingQty;

    @SerializedName("AssignedID")
    private String assignedID;

    @SerializedName("ExpDate")
    private String expDate;

    @SerializedName("ProjectNo")
    private String projectNo;

    @SerializedName("SkipReason")
    private String skipReason;

    @SerializedName("KitId")
    private String kitId;

    @SerializedName("CartonNo")
    private String cartonNo;

    @SerializedName("IsDam")
    private String isDam;

    @SerializedName("HasDis")
    private String hasDis;

    @SerializedName("Lineno")
    private String lineno;

    @SerializedName("AccountId")
    private String accountId;

    @SerializedName("MCode")
    private String mCode;

    @SerializedName("MDescription")
    private String mDescription;

    @SerializedName("Qty")
    private String qty;

    @SerializedName("ToCartonNo")
    private String ToCartonNo;

    @SerializedName("SODetailsID")
    private String SODetailsID;

    @SerializedName("POSOHeaderId")
    private String pOSOHeaderId;

    @SerializedName("SkipQty")
    private String skipQty;

    @SerializedName("SLoc")
    private String sLoc;

    @SerializedName("VLPDNumber")
    private String vLPDNumber;

    @SerializedName("VLPDId")
    private String vLPDId;

    @SerializedName("LocationId")
    private String locationId;

    @SerializedName("CartonID")
    private String cartonID;

    @SerializedName("TransferRequestDetailsId")
    private String transferRequestDetailsId;

    @SerializedName("TransferRequestId")
    private String transferRequestId;

    @SerializedName("SLocId")
    private String sLocId;

    @SerializedName("StorageLocationID")
    private String storageLocationID;

    @SerializedName("PickedId")
    private String PickedId;

    @SerializedName("AccountID")
    private String AccountID;


    public OutbountDTO() {

    }

    public OutbountDTO(Set<? extends Map.Entry<?, ?>> entries) {
        for (Map.Entry<?, ?> entry : entries) {

            switch (entry.getKey().toString()) {

                case "OutboundID":
                    if (entry.getValue() != null) {
                        this.setOutboundID(entry.getValue().toString());
                    }
                    break;
                case "PickRefNo":
                    if (entry.getValue() != null) {
                        this.setPickRefNo((List<String>) entry.getValue());
                    }
                    break;
                case "MRP":
                    if (entry.getValue() != null) {
                        this.setMRP(entry.getValue().toString());
                    }
                    break;
                case "MOP":
                    if (entry.getValue() != null) {
                        this.setMOP(entry.getValue().toString());
                    }
                    break;
                case "SKU":
                    if (entry.getValue() != null) {
                        this.setSKU(entry.getValue().toString());
                    }
                    break;
                case "SerialNo":
                    if (entry.getValue() != null) {
                        this.setSerialNo(entry.getValue().toString());
                    }
                    break;
                case "MfgDate":
                    if (entry.getValue() != null) {
                        this.setMfgDate(entry.getValue().toString());
                    }
                    break;
                case "Location":
                    if (entry.getValue() != null) {
                        this.setLocation(entry.getValue().toString());
                    }
                    break;
                case "PalletNo":
                    if (entry.getValue() != null) {
                        this.setPalletNo(entry.getValue().toString());
                    }
                    break;
                case "DockNumber":
                    if (entry.getValue() != null) {
                        this.setDockNumber(entry.getValue().toString());
                    }
                    break;
                case "RequiredQty":
                    if (entry.getValue() != null) {
                        this.setRequiredQty(entry.getValue().toString());
                    }
                    break;
                case "PickedQty":
                    if (entry.getValue() != null) {
                        this.setPickedQty(entry.getValue().toString());
                    }
                    break;
                case "AllowNestedInventoryDispatch":
                    if (entry.getValue() != null) {
                        this.setAllowNestedInventoryDispatch(Boolean.parseBoolean(entry.getValue().toString()));
                    }
                    break;
                case "AllowDispatchOfOLDMRP":
                    if (entry.getValue() != null) {
                        this.setAllowDispatchOfOLDMRP(Boolean.parseBoolean(entry.getValue().toString()));
                    }
                    break;
                case "AllowCrossDocking":
                    if (entry.getValue() != null) {
                        this.setAllowCrossDocking(Boolean.parseBoolean(entry.getValue().toString()));
                    }
                    break;
                case "StrictComplianceToPicking":
                    if (entry.getValue() != null) {
                        this.setStrictComplianceToPicking(Boolean.parseBoolean(entry.getValue().toString()));
                    }
                    break;
                case "AutoReconsileInventoryOnSkip":
                    if (entry.getValue() != null) {
                        this.setAutoReconsileInventoryOnSkip(Boolean.parseBoolean(entry.getValue().toString()));
                    }
                    break;

                case "LoadList":
                    if (entry.getValue() != null) {
                        List<LinkedTreeMap<?, ?>> treemapList = (List<LinkedTreeMap<?, ?>>) entry.getValue();
                        List<LoadDTO> lstLoad = new ArrayList<LoadDTO>();
                        if (treemapList.size() > 0 && treemapList != null) {
                            for (int i = 0; i < treemapList.size(); i++) {
                                LoadDTO dto = new LoadDTO(treemapList.get(i).entrySet());
                                lstLoad.add(dto);
                            }
                        }
                        this.setLoadList(lstLoad);
                    }
                    break;
                case "SuggestionID":
                    if (entry.getValue() != null) {
                        this.setSuggestionID(entry.getValue().toString());
                    }
                    break;
                case "RevertQty":
                    if (entry.getValue() != null) {
                        this.setRevertQty(entry.getValue().toString());
                    }
                    break;
                case "CustomerCode":
                    if (entry.getValue() != null) {
                        this.setCustomerCode(entry.getValue().toString());
                    }
                    break;
                case "MaterialMasterId":
                    if (entry.getValue() != null) {
                        this.setMaterialMasterId(entry.getValue().toString());
                    }
                    break;

                case "AccountId":
                    if (entry.getValue() != null) {
                        this.setAccountId(entry.getValue().toString());
                    }
                    break;


                case "OBDNo":
                    if (entry.getValue() != null) {
                        this.setoBDNo(entry.getValue().toString());
                    }
                    break;

                case "BatchNo":
                    if (entry.getValue() != null) {
                        this.setBatchNo(entry.getValue().toString());
                    }
                    break;
                case "AssignedQuantity":
                    if (entry.getValue() != null) {
                        this.setAssignedQuantity(entry.getValue().toString());
                    }
                    break;
                case "PendingQty":
                    if (entry.getValue() != null) {
                        this.setPendingQty(entry.getValue().toString());
                    }
                    break;

                case "AssignedID":
                    if (entry.getValue() != null) {
                        this.setAssignedID(entry.getValue().toString());
                    }
                    break;

                case "ExpDate":
                    if (entry.getValue() != null) {
                        this.setExpDate(entry.getValue().toString());
                    }
                    break;

                case "ProjectNo":
                    if (entry.getValue() != null) {
                        this.setProjectNo(entry.getValue().toString());
                    }
                    break;

                case "SkipReason":
                    if (entry.getValue() != null) {
                        this.setSkipReason(entry.getValue().toString());
                    }
                    break;
                case "KitId":
                    if (entry.getValue() != null) {
                        this.setKitId(entry.getValue().toString());
                    }
                    break;

                case "CartonNo":
                    if (entry.getValue() != null) {
                        this.setCartonNo(entry.getValue().toString());
                    }
                    break;

                case "IsDam":
                    if (entry.getValue() != null) {
                        this.setIsDam(entry.getValue().toString());
                    }
                    break;
                case "HasDis":
                    if (entry.getValue() != null) {
                        this.setHasDis(entry.getValue().toString());
                    }
                    break;

                case "Lineno":
                    if (entry.getValue() != null) {
                        this.setLineno(entry.getValue().toString());
                    }
                    break;

                case "MCode":
                    if (entry.getValue() != null) {
                        this.setmCode(entry.getValue().toString());
                    }
                    break;

                case "MDescription":
                    if (entry.getValue() != null) {
                        this.setmDescription(entry.getValue().toString());
                    }
                    break;

                case "Qty":
                    if (entry.getValue() != null) {
                        this.setQty(entry.getValue().toString());
                    }
                    break;

                case "ToCartonNo":
                    if (entry.getValue() != null) {
                        this.setToCartonNo(entry.getValue().toString());
                    }
                    break;
                case "SODetailsID":
                    if (entry.getValue() != null) {
                        this.setSODetailsID(entry.getValue().toString());
                    }
                    break;

                case "POSOHeaderId":
                    if (entry.getValue() != null) {
                        this.setpOSOHeaderId(entry.getValue().toString());
                    }
                    break;

                case "SkipQty":
                    if (entry.getValue() != null) {
                        this.setSkipQty(entry.getValue().toString());
                    }
                    break;

                case "SLoc":
                    if (entry.getValue() != null) {
                        this.setsLoc(entry.getValue().toString());
                    }
                    break;


                case "VLPDNumber":
                    if (entry.getValue() != null) {
                        this.setvLPDNumber(entry.getValue().toString());
                    }
                    break;

                case "VLPDId":
                    if (entry.getValue() != null) {
                        this.setvLPDId(entry.getValue().toString());
                    }
                    break;

                case "LocationId":
                    if (entry.getValue() != null) {
                        this.setLocationId(entry.getValue().toString());
                    }
                    break;
                case "CartonID":
                    if (entry.getValue() != null) {
                        this.setCartonID(entry.getValue().toString());
                    }
                    break;

                case "TransferRequestDetailsId":
                    if (entry.getValue() != null) {
                        this.setTransferRequestDetailsId(entry.getValue().toString());
                    }
                    break;

                case "TransferRequestId":
                    if (entry.getValue() != null) {
                        this.setTransferRequestId(entry.getValue().toString());
                    }
                    break;
                case "SLocId":
                    if (entry.getValue() != null) {
                        this.setsLocId(entry.getValue().toString());
                    }
                    break;


                case "StorageLocationID":
                    if (entry.getValue() != null) {
                        this.setStorageLocationID(entry.getValue().toString());
                    }
                    break;

                case "PickedId":
                    if(entry.getValue()!=null) {
                        this.setPickedId(entry.getValue().toString());
                    }
                    break;

                case "AccountID":
                    if(entry.getValue()!=null) {
                        this.setAccountID(entry.getValue().toString());
                    }
                    break;





            }
        }
    }


    public String getPickedId() {
        return PickedId;
    }

    public void setPickedId(String pickedId) {
        PickedId = pickedId;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getStorageLocationID() {
        return storageLocationID;
    }

    public void setStorageLocationID(String storageLocationID) {
        this.storageLocationID = storageLocationID;
    }

    public String getsLocId() {
        return sLocId;
    }

    public void setsLocId(String sLocId) {
        this.sLocId = sLocId;
    }

    public String getTransferRequestId() {
        return transferRequestId;
    }

    public void setTransferRequestId(String transferRequestId) {
        this.transferRequestId = transferRequestId;
    }

    public String getTransferRequestDetailsId() {
        return transferRequestDetailsId;
    }

    public void setTransferRequestDetailsId(String transferRequestDetailsId) {
        this.transferRequestDetailsId = transferRequestDetailsId;
    }

    public String getCartonID() {
        return cartonID;
    }

    public void setCartonID(String cartonID) {
        this.cartonID = cartonID;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getvLPDId() {
        return vLPDId;
    }

    public void setvLPDId(String vLPDId) {
        this.vLPDId = vLPDId;
    }

    public String getvLPDNumber() {
        return vLPDNumber;
    }

    public void setvLPDNumber(String vLPDNumber) {
        this.vLPDNumber = vLPDNumber;
    }

    public String getsLoc() {
        return sLoc;
    }

    public void setsLoc(String sLoc) {
        this.sLoc = sLoc;
    }

    public String getSkipQty() {
        return skipQty;
    }

    public void setSkipQty(String skipQty) {
        this.skipQty = skipQty;
    }

    public String getpOSOHeaderId() {
        return pOSOHeaderId;
    }

    public void setpOSOHeaderId(String pOSOHeaderId) {
        this.pOSOHeaderId = pOSOHeaderId;
    }

    public String getSODetailsID() {
        return SODetailsID;
    }

    public void setSODetailsID(String SODetailsID) {
        this.SODetailsID = SODetailsID;
    }

    public String getToCartonNo() {
        return ToCartonNo;
    }

    public void setToCartonNo(String toCartonNo) {
        ToCartonNo = toCartonNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getmCode() {

        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getLineno() {
        return lineno;
    }

    public void setLineno(String lineno) {
        this.lineno = lineno;
    }

    public String getIsDam() {
        return isDam;
    }

    public void setIsDam(String isDam) {
        this.isDam = isDam;
    }

    public String getHasDis() {
        return hasDis;
    }

    public void setHasDis(String hasDis) {
        this.hasDis = hasDis;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getSkipReason() {
        return skipReason;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getAssignedID() {
        return assignedID;
    }

    public void setAssignedID(String assignedID) {
        this.assignedID = assignedID;
    }

    public String getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty(String pendingQty) {
        this.pendingQty = pendingQty;
    }

    public String getAssignedQuantity() {
        return assignedQuantity;
    }

    public void setAssignedQuantity(String assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getoBDNo() {
        return oBDNo;
    }

    public void setoBDNo(String oBDNo) {
        this.oBDNo = oBDNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMaterialMasterId() {
        return MaterialMasterId;
    }

    public void setMaterialMasterId(String materialMasterId) {
        MaterialMasterId = materialMasterId;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getRevertQty() {
        return RevertQty;
    }

    public void setRevertQty(String revertQty) {
        RevertQty = revertQty;
    }

    public String getSuggestionID() {
        return SuggestionID;
    }

    public void setSuggestionID(String suggestionID) {
        SuggestionID = suggestionID;
    }

    public List<LoadDTO> getLoadList() {
        return LoadList;
    }

    public void setLoadList(List<LoadDTO> loadList) {
        LoadList = loadList;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }

    public Boolean getAllowNestedInventoryDispatch() {
        return AllowNestedInventoryDispatch;
    }

    public void setAllowNestedInventoryDispatch(Boolean allowNestedInventoryDispatch) {
        AllowNestedInventoryDispatch = allowNestedInventoryDispatch;
    }

    public Boolean getAllowDispatchOfOLDMRP() {
        return AllowDispatchOfOLDMRP;
    }

    public void setAllowDispatchOfOLDMRP(Boolean allowDispatchOfOLDMRP) {
        AllowDispatchOfOLDMRP = allowDispatchOfOLDMRP;
    }

    public Boolean getAllowCrossDocking() {
        return AllowCrossDocking;
    }

    public void setAllowCrossDocking(Boolean allowCrossDocking) {
        AllowCrossDocking = allowCrossDocking;
    }

    public Boolean getStrictComplianceToPicking() {
        return StrictComplianceToPicking;
    }

    public void setStrictComplianceToPicking(Boolean strictComplianceToPicking) {
        StrictComplianceToPicking = strictComplianceToPicking;
    }

    public Boolean getAutoReconsileInventoryOnSkip() {
        return AutoReconsileInventoryOnSkip;
    }

    public void setAutoReconsileInventoryOnSkip(Boolean autoReconsileInventoryOnSkip) {
        AutoReconsileInventoryOnSkip = autoReconsileInventoryOnSkip;
    }

    public String getOutboundID() {
        return outboundID;
    }

    public void setOutboundID(String outboundID) {
        this.outboundID = outboundID;
    }

    public List<String> getPickRefNo() {
        return pickRefNo;
    }

    public void setPickRefNo(List<String> pickRefNo) {
        this.pickRefNo = pickRefNo;
    }


    public String getMOP() {
        return MOP;
    }

    public void setMOP(String MOP) {
        this.MOP = MOP;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getMfgDate() {
        return MfgDate;
    }

    public void setMfgDate(String mfgDate) {
        MfgDate = mfgDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getMaterialDamaged() {
        return isMaterialDamaged;
    }

    public void setMaterialDamaged(Boolean materialDamaged) {
        isMaterialDamaged = materialDamaged;
    }

    public Boolean getMaterialNotFound() {
        return isMaterialNotFound;
    }

    public void setMaterialNotFound(Boolean materialNotFound) {
        isMaterialNotFound = materialNotFound;
    }

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRequiredQty() {
        return requiredQty;
    }

    public void setRequiredQty(String requiredQty) {
        this.requiredQty = requiredQty;
    }

    public String getPickedQty() {
        return pickedQty;
    }

    public void setPickedQty(String pickedQty) {
        this.pickedQty = pickedQty;
    }

    public String getSelectedPickRefNumber() {
        return SelectedPickRefNumber;
    }

    public void setSelectedPickRefNumber(String selectedPickRefNumber) {
        SelectedPickRefNumber = selectedPickRefNumber;
    }


    public String getSelectedLoadSheetNumber() {
        return SelectedLoadSheetNumber;
    }

    public void setSelectedLoadSheetNumber(String selectedLoadSheetNumber) {
        SelectedLoadSheetNumber = selectedLoadSheetNumber;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }
}