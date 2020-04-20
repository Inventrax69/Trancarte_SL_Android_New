package com.inventrax.falconsl_new.interfaces;

import com.inventrax.falconsl_new.pojos.WMSCoreMessage;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {


    // Login
    @POST("Login/UserLogin")
    Call<String> UserLogin(@Body WMSCoreMessage oRequest);

    // Receiving
    @POST("Inbound/GetStoreRefNos")
    Call<String> GetStoreRefNos(@Body WMSCoreMessage oRequest);

    @POST("Inbound/CheckContainer")
    Call<String> CheckContainer(@Body WMSCoreMessage oRequest);

    @POST("Inbound/GetStorageLocations")
    Call<String> GetStorageLocations(@Body WMSCoreMessage oRequest);

    @POST("Inbound/UpdateReceiveItemForHHT")
    Call<String> UpdateReceiveItemForHHT(@Body WMSCoreMessage oRequest);

    @POST("Inbound/GetReceivedQty")
    Call<String> GetReceivedQty(@Body WMSCoreMessage oRequest);

    @POST("Inbound/CheckDockGoodsIn")
    Call<String> CheckDockGoodsIn(@Body WMSCoreMessage oRequest);

    // Putaway
    @POST("Inbound/GetSkipReasonList")
    Call<String> GetSkipReasonList(@Body WMSCoreMessage oRequest);

    @POST("Inbound/GetConatinerLocation")
    Call<String> GetConatinerLocation(@Body WMSCoreMessage oRequest);

    @POST("Inbound/GetItemTOPutAway")
    Call<String> GetItemTOPutAway(@Body WMSCoreMessage oRequest);

    @POST("Inbound/SkipItem")
    Call<String> SkipItem(@Body WMSCoreMessage oRequest);

    @POST("Inbound/UpsertPutAwayItem")
    Call<String> UpsertPutAwayItem(@Body WMSCoreMessage oRequest);

    @POST("Inbound/CheckPutAwayItemQty")
    Call<String> CheckPutAwayItemQty(@Body WMSCoreMessage oRequest);

    // LiveStock
    @POST("HouseKeeping/GetTenants")
    Call<String> GetTenants(@Body WMSCoreMessage oRequest);

    @POST("HouseKeeping/GetWarehouse")
    Call<String> GetWarehouse(@Body WMSCoreMessage oRequest);

    @POST("HouseKeeping/CheckLocationForLiveStock")
    Call<String> CheckLocationForLiveStock(@Body WMSCoreMessage oRequest);

    @POST("HouseKeeping/CheckTenatMaterial")
    Call<String> CheckTenatMaterial(@Body WMSCoreMessage oRequest);

    @POST("HouseKeeping/GetActivestock")
    Call<String> GetActivestock(@Body WMSCoreMessage oRequest);

    @POST("HouseKeeping/ValidateCartonForLiveStock")
    Call<String> ValidateCartonForLiveStock(@Body WMSCoreMessage oRequest);

    // Delete VLPD Picked Items
    @POST("Outbound/GetOpenVLPDNos")
    Call<String> GetOpenVLPDNos(@Body WMSCoreMessage oRequest);

    @POST("Outbound/GetVLPDPickedList")
    Call<String> GetVLPDPickedList(@Body WMSCoreMessage oRequest);

    // Delete OBD Picked Items
    @POST("Outbound/GetobdRefNos")
    Call<String> GetobdRefNos(@Body WMSCoreMessage oRequest);

    @POST("Outbound/GetOBDPickedList")
    Call<String> GetOBDPickedList(@Body WMSCoreMessage oRequest);

    @POST("Outbound/DeleteVLPDPickedItems")
    Call<String> DeleteVLPDPickedItems(@Body WMSCoreMessage oRequest);

    // Cycle Count
    @POST("CycleCount/GetCCNames")
    Call<String> GetCCNames(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/IsBlockedLocation")
    Call<String> IsBlockedLocation(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/BlockLocationForCycleCount")
    Call<String> BlockLocationForCycleCount(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/ChekPalletLocation")
    Call<String> ChekPalletLocation(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/CheckMaterialAvailablilty")
    Call<String> CheckMaterialAvailablilty(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/UpsertCycleCount")
    Call<String> UpsertCycleCount(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/GetCycleCountInformation")
    Call<String> GetCycleCountInformation(@Body WMSCoreMessage oRequest);

    @POST("CycleCount/ReleaseCycleCountLocation")
    Call<String> ReleaseCycleCountLocation(@Body WMSCoreMessage oRequest);

    @POST("Outbound/GetLoadSheetNo")
    Call<String> GetLoadSheetNo(@Body WMSCoreMessage oRequest);

    @POST("Outbound/GetOBDItemToPick")
    Call<String> GetOBDItemToPick(@Body WMSCoreMessage oRequest);

    @POST("Outbound/CheckContainerOBD")
    Call<String> CheckContainerOBD(@Body WMSCoreMessage oRequest);

    @POST("Outbound/OBDSkipItem")
    Call<String> OBDSkipItem(@Body WMSCoreMessage oRequest);

    @POST("Transfers/GetTransferReqNos")
    Call<String> GetTransferReqNos(@Body WMSCoreMessage oRequest);

    @POST("Transfers/TransferPalletToLocation")
    Call<String> TransferPalletToLocation(@Body WMSCoreMessage oRequest);

    @POST("Outbound/CheckStrickyCompliance")
    Call<String> CheckStrickyCompliance(@Body WMSCoreMessage oRequest);

    @POST("Outbound/VLPDSkipItem")
    Call<String> VLPDSkipItem(@Body WMSCoreMessage oRequest);

    @POST("Outbound/GetItemToPick")
    Call<String> GetItemToPick(@Body WMSCoreMessage oRequest);

    @POST("Outbound/UpdatePickItem")
    Call<String> UpdatePickItem(@Body WMSCoreMessage oRequest);

    @POST("Outbound/UpsertPickItem")
    Call<String> UpsertPickItem(@Body WMSCoreMessage oRequest);

    @POST("Outbound/FetchInventoryForLoadSheet")
    Call<String> FetchInventoryForLoadSheet(@Body WMSCoreMessage oRequest);

    @POST("Outbound/ConfirmLoading")
    Call<String> ConfirmLoading(@Body WMSCoreMessage oRequest);

    @POST("Outbound/RevertLoading")
    Call<String> RevertLoading(@Body WMSCoreMessage oRequest);

    @POST("Outbound/LoadingComplete")
    Call<String> LoadingComplete(@Body WMSCoreMessage oRequest);

    @POST("Outbound/ValidatePalletAtPicking")
    Call<String> ValidatePalletAtPicking(@Body WMSCoreMessage oRequest);

    @POST("Transfers/UpsertBinToBinTransferItem")
    Call<String> UpsertBinToBinTransferItem(@Body WMSCoreMessage oRequest);

    @POST("Transfers/ChekContainerLocation")
    Call<String> ChekContainerLocation(@Body WMSCoreMessage oRequest);

    @POST("Transfers/GetAvailbleQtyList")
    Call<String> GetAvailbleQtyList(@Body WMSCoreMessage oRequest);

    @POST("Transfers/GetBinToBinStorageLocations")
    Call<String> GetBinToBinStorageLocations(@Body WMSCoreMessage oRequest);

    @POST("Exception/LogException")
    Call<String> LogException(@Body WMSCoreMessage oRequest);

    // Sorting
    @POST("Outbound/GetOpenVLPDNosForSorting")
    Call<String> GetOpenVLPDNosForSorting(@Body WMSCoreMessage oRequest);

    @POST("Outbound/GetSortingList")
    Call<String> GetSortingList(@Body WMSCoreMessage oRequest);

    //scan
    @POST("Scan/ValidateLocation")
    Call<String> ValidateLocation(@Body WMSCoreMessage oRequest);

    @POST("Scan/ValiDateMaterial")
    Call<String> ValiDateMaterial(@Body WMSCoreMessage oRequest);

    @POST("Scan/ValidatePallet")
    Call<String> ValidatePallet(@Body WMSCoreMessage oRequest);


}