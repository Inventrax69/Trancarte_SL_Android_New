package com.inventrax.falconsl_new.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cipherlab.barcode.GeneralString;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.inventrax.falconsl_new.R;
import com.inventrax.falconsl_new.adapters.LiveStockAdapter;
import com.inventrax.falconsl_new.common.Common;
import com.inventrax.falconsl_new.common.constants.EndpointConstants;
import com.inventrax.falconsl_new.common.constants.ErrorMessages;
import com.inventrax.falconsl_new.interfaces.ApiInterface;
import com.inventrax.falconsl_new.pojos.HouseKeepingDTO;
import com.inventrax.falconsl_new.pojos.InventoryDTO;
import com.inventrax.falconsl_new.pojos.WMSCoreMessage;
import com.inventrax.falconsl_new.pojos.WMSExceptionMessage;
import com.inventrax.falconsl_new.searchableSpinner.SearchableSpinner;
import com.inventrax.falconsl_new.services.RestService;
import com.inventrax.falconsl_new.util.DialogUtils;
import com.inventrax.falconsl_new.util.ExceptionLoggerUtils;
import com.inventrax.falconsl_new.util.ProgressDialogUtils;
import com.inventrax.falconsl_new.util.ScanValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karthik.m on 05/08/2018.
 */

public class LiveStockFragment extends Fragment implements View.OnClickListener, BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {


    private static final String classCode = "API_FRAG_LIVESTOCK";
    private View rootView;

    private TextView tvScanRSN, tvScanLocation, tvScanPallet, lblScannedData;
    private SearchableSpinner spinnerSelectTenant, spinnerSelectWH;
    private CardView cvScanRSN, cvScanLocation, cvScanPallet;
    private ImageView ivScanRSN, ivScanLocation, ivScanPallet;
    private Button btnSearch, btnClear;
    private RecyclerView recyclerViewLiveStock;
    private LinearLayoutManager linearLayoutManager;

    String scanner = null;
    private IntentFilter filter;
    private WMSCoreMessage core;
    String getScanner = null;
    private Gson gson;
    private ScanValidator scanValidator;
    private static BarcodeReader barcodeReader;
    private AidcManager manager;
    private Common common;

    private ExceptionLoggerUtils exceptionLoggerUtils;
    private ErrorMessages errorMessages;
    List<HouseKeepingDTO> lstTenants = null;
    List<HouseKeepingDTO> lstWarehouse = null;

    private Boolean isLocationScanned = false, isSKUScanned = false, isPalletScanned = false;
    private String scannedLocation = null, scannedPallet = "", scannedSKU = null;
    private String userId = "", scanType = "", accountId = "", selectedTenant = "",
            selectedWH = "", tenantId = "", warehouseId = "";
    private String batch = "", serialNo = "", prjRef = "", mfg = "", exp = "", mrp = "";

    // Cipher Barcode Scanner
    private final BroadcastReceiver myDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            scanner = intent.getStringExtra(GeneralString.BcReaderData);  // Scanned Barcode info
            ProcessScannedinfo(scanner.trim().toString());
        }
    };

    public LiveStockFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_livestock, container, false);
        loadFormControls();

        return rootView;
    }

    private void loadFormControls() {

        SharedPreferences sp = getActivity().getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
        userId = sp.getString("RefUserId", "");
        scanType = sp.getString("scanType", "");
        accountId = sp.getString("AccountId", "");

        cvScanRSN = (CardView) rootView.findViewById(R.id.cvScanRSN);
        cvScanLocation = (CardView) rootView.findViewById(R.id.cvScanLocation);
        cvScanPallet = (CardView) rootView.findViewById(R.id.cvScanPallet);

        ivScanRSN = (ImageView) rootView.findViewById(R.id.ivScanRSN);
        ivScanLocation = (ImageView) rootView.findViewById(R.id.ivScanLocation);
        ivScanPallet = (ImageView) rootView.findViewById(R.id.ivScanPallet);

        btnClear = (Button) rootView.findViewById(R.id.btnClear);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);

        tvScanRSN = (TextView) rootView.findViewById(R.id.tvScanRSN);
        tvScanLocation = (TextView) rootView.findViewById(R.id.tvScanLocation);
        tvScanPallet = (TextView) rootView.findViewById(R.id.tvScanPallet);
        lblScannedData = (TextView) rootView.findViewById(R.id.lblScannedData);

        lstTenants = new ArrayList<HouseKeepingDTO>();
        lstWarehouse = new ArrayList<HouseKeepingDTO>();

        recyclerViewLiveStock = (RecyclerView) rootView.findViewById(R.id.recycler_view_liveStock);
        recyclerViewLiveStock.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());

        // use a linear layout manager
        recyclerViewLiveStock.setLayoutManager(linearLayoutManager);

        spinnerSelectTenant = (SearchableSpinner) rootView.findViewById(R.id.spinnerSelectTenant);
        spinnerSelectTenant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTenant = spinnerSelectTenant.getSelectedItem().toString();
                getTenantId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSelectWH = (SearchableSpinner) rootView.findViewById(R.id.spinnerSelectWH);
        spinnerSelectWH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedWH = spinnerSelectWH.getSelectedItem().toString();
                getWarehouseId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        exceptionLoggerUtils = new ExceptionLoggerUtils();
        errorMessages = new ErrorMessages();

        // For Cipher Barcode reader
        Intent RTintent = new Intent("sw.reader.decode.require");
        RTintent.putExtra("Enable", true);
        getActivity().sendBroadcast(RTintent);
        this.filter = new IntentFilter();
        this.filter.addAction("sw.reader.decode.complete");
        getActivity().registerReceiver(this.myDataReceiver, this.filter);


        common = new Common();
        gson = new GsonBuilder().create();

        btnClear.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        //For Honeywell
        AidcManager.create(getActivity(), new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {

                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
                try {
                    barcodeReader.claim();
                    HoneyWellBarcodeListeners();

                } catch (ScannerUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });

        // To get Tenants
        getTenants();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnClear:
                clearFields();
                break;

            case R.id.btnSearch:

                if (isLocationScanned || isSKUScanned || isPalletScanned) {

                    // To get Active Stock
                    GetActiveStockData();

                } else {
                    common.showUserDefinedAlertType(errorMessages.EMC_0058, getActivity(), getContext(), "Error");
                    return;
                }


                break;

        }
    }

    public void getTenantId() {

        for (HouseKeepingDTO oHouseKeeping : lstTenants)

        {
            if (oHouseKeeping.getTenantName().equals(selectedTenant)) {

                tenantId = oHouseKeeping.getTenantID();

                getWarehouse();
            }
        }
    }

    public void getWarehouseId() {

        for (HouseKeepingDTO oHouseKeeping : lstWarehouse)

        {
            if (oHouseKeeping.getWarehouse().equals(selectedWH)) {

                warehouseId = oHouseKeeping.getWarehouseId();

            }
        }
    }

    // honeywell
    @Override
    public void onBarcodeEvent(final BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // update UI to reflect the data
                getScanner = barcodeReadEvent.getBarcodeData();
                ProcessScannedinfo(getScanner);

            }

        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {

    }

    //Honeywell Barcode reader Properties
    public void HoneyWellBarcodeListeners() {

        barcodeReader.addTriggerListener(this);

        if (barcodeReader != null) {
            // set the trigger mode to client control
            barcodeReader.addBarcodeListener(this);
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
            } catch (UnsupportedPropertyException e) {
                // Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show();
            }

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Enable bad read response
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            barcodeReader.setProperties(properties);
        }
    }


    //Assigning scanned value to the respective fields
    public void ProcessScannedinfo(String scannedData) {

        if (scannedData != null) {

            if (ScanValidator.isContainerScanned(scannedData)) {

                tvScanPallet.setText(scannedData);

                scannedPallet = scannedData;

                validatePalletCode();

                return;
            }

            if (ScanValidator.isItemScanned(scannedData)) {

                lblScannedData.setText(scannedData.split("[|]")[0]);
                scannedSKU = scannedData.split("[|]")[0];
                batch = scannedData.split("[|]")[1];
                serialNo = scannedData.split("[|]")[2];
                mfg = scannedData.split("[|]")[3];
                exp = scannedData.split("[|]")[4];
                prjRef = scannedData.split("[|]")[5];
                mrp = scannedData.split("[|]")[7];

                // Validate Material
                validateMaterial();
                return;
            }

            if (ScanValidator.isLocationScanned(scannedData)) {

                tvScanLocation.setText(scannedData);
                scannedLocation = scannedData;

                // Validate Location
                validateLocationCode();

                return;

            }
        }
    }


    public void clearFields() {

        cvScanLocation.setCardBackgroundColor(getResources().getColor(R.color.locationColor));
        ivScanLocation.setImageResource(R.drawable.fullscreen_img);

        cvScanPallet.setCardBackgroundColor(getResources().getColor(R.color.palletColor));
        ivScanPallet.setImageResource(R.drawable.fullscreen_img);

        cvScanRSN.setCardBackgroundColor(getResources().getColor(R.color.skuColor));
        ivScanRSN.setImageResource(R.drawable.fullscreen_img);


        lblScannedData.setText("");

        recyclerViewLiveStock.setAdapter(null);

        scannedSKU = "";
        isLocationScanned = false;
        isSKUScanned = false;
        isPalletScanned = false;

        scannedLocation = "";
        scannedPallet = "";
        scannedSKU = "";

        tvScanLocation.setText(R.string.scanLocation);
        tvScanPallet.setText(R.string.scanPallet);

        serialNo = "";
        batch = "";
        mrp = "";
        mfg = "";
        exp = "";
        prjRef = "";

    }

    public void getTenants() {

        try {


            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.HouseKeepingDTO, getContext());
            HouseKeepingDTO houseKeepingDTO = new HouseKeepingDTO();
            houseKeepingDTO.setAccountID(accountId);
            message.setEntityObject(houseKeepingDTO);


            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                call = apiService.GetTenants(message);
                ProgressDialogUtils.showProgressDialog("Please Wait");
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;

                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);

            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);
                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;
                                for (int i = 0; i < _lExceptions.size(); i++) {

                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());


                                }
                                ProgressDialogUtils.closeProgressDialog();
                                common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                            } else {

                                List<LinkedTreeMap<?, ?>> _lstActiveStock = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lstActiveStock = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                List<HouseKeepingDTO> lstDto = new ArrayList<HouseKeepingDTO>();
                                List<String> _lstStock = new ArrayList<>();

                                ProgressDialogUtils.closeProgressDialog();

                                for (int i = 0; i < _lstActiveStock.size(); i++) {
                                    HouseKeepingDTO dto = new HouseKeepingDTO(_lstActiveStock.get(i).entrySet());
                                    lstDto.add(dto);
                                    lstTenants.add(dto);
                                }

                                for (int i = 0; i < lstDto.size(); i++) {

                                    _lstStock.add(lstDto.get(i).getTenantName());

                                }


                                ArrayAdapter liveStockAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, _lstStock);
                                spinnerSelectTenant.setAdapter(liveStockAdapter);


                            }
                        } catch (Exception ex) {
                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_02", getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ProgressDialogUtils.closeProgressDialog();
                        }


                    }

                    // response object fails
                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        //Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_03", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_04", getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
        }
    }

    public void getWarehouse() {

        try {


            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.HouseKeepingDTO, getContext());
            HouseKeepingDTO houseKeepingDTO = new HouseKeepingDTO();
            houseKeepingDTO.setAccountID(accountId);
            houseKeepingDTO.setTenantID(tenantId);
            message.setEntityObject(houseKeepingDTO);


            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                call = apiService.GetWarehouse(message);
                ProgressDialogUtils.showProgressDialog("Please Wait");
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;

                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);

            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);
                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;
                                for (int i = 0; i < _lExceptions.size(); i++) {

                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());


                                }
                                ProgressDialogUtils.closeProgressDialog();
                                common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                            } else {

                                List<LinkedTreeMap<?, ?>> _lstActiveStock = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lstActiveStock = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                List<HouseKeepingDTO> lstDto = new ArrayList<HouseKeepingDTO>();
                                List<String> _lstStock = new ArrayList<>();

                                ProgressDialogUtils.closeProgressDialog();

                                for (int i = 0; i < _lstActiveStock.size(); i++) {
                                    HouseKeepingDTO dto = new HouseKeepingDTO(_lstActiveStock.get(i).entrySet());
                                    lstDto.add(dto);
                                    lstWarehouse.add(dto);
                                }

                                for (int i = 0; i < lstDto.size(); i++) {

                                    _lstStock.add(lstDto.get(i).getWarehouse());

                                }


                                ArrayAdapter liveStockAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, _lstStock);
                                spinnerSelectWH.setAdapter(liveStockAdapter);


                            }
                        } catch (Exception ex) {
                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_02", getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ProgressDialogUtils.closeProgressDialog();
                        }


                    }

                    // response object fails
                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        //Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_03", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_04", getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
        }
    }


    public void validateLocationCode() {
        try {

            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.Inventory, getActivity());
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setAccountID(accountId);
            inventoryDTO.setTenantID(tenantId);
            inventoryDTO.setWarehouse(selectedWH);
            inventoryDTO.setWarehouseId(warehouseId);
            inventoryDTO.setLocationCode(scannedLocation);
            message.setEntityObject(inventoryDTO);

            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                ProgressDialogUtils.showProgressDialog("Please Wait");
                call = apiService.CheckLocationForLiveStock(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;
                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                common.showUserDefinedAlertType(errorMessages.EMC_0002, getActivity(), getContext(), "Error");
            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);

                            // if any Exception throws
                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;
                                for (int i = 0; i < _lExceptions.size(); i++) {

                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());
                                    //etLocation.setText("");
                                    scannedLocation = "";
                                    ProgressDialogUtils.closeProgressDialog();
                                    cvScanLocation.setCardBackgroundColor(getResources().getColor(R.color.white));
                                    ivScanLocation.setImageResource(R.drawable.warning_img);
                                    common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                                    return;
                                }
                            } else {
                                List<LinkedTreeMap<?, ?>> _lResult = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lResult = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                InventoryDTO dto = null;
                                ProgressDialogUtils.closeProgressDialog();

                                for (int i = 0; i < _lResult.size(); i++) {

                                    dto = new InventoryDTO(_lResult.get(i).entrySet());
                                    if (dto.getResult().equals("1")) {

                                        isLocationScanned = true;

                                        cvScanLocation.setCardBackgroundColor(getResources().getColor(R.color.white));
                                        ivScanLocation.setImageResource(R.drawable.check);
                                    } else {
                                        cvScanLocation.setCardBackgroundColor(getResources().getColor(R.color.white));
                                        ivScanLocation.setImageResource(R.drawable.invalid_cross);
                                        common.showUserDefinedAlertType(errorMessages.EMC_0016, getActivity(), getContext(), "Error");
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_02", getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ProgressDialogUtils.closeProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        ProgressDialogUtils.closeProgressDialog();
                        common.showUserDefinedAlertType(errorMessages.EMC_0002, getActivity(), getContext(), "Error");
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_03", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                common.showUserDefinedAlertType(errorMessages.EMC_0001, getActivity(), getContext(), "Error");
            }
        } catch (Exception ex) {
            ProgressDialogUtils.closeProgressDialog();
            common.showUserDefinedAlertType(errorMessages.EMC_0003, getActivity(), getContext(), "Error");
        }
    }


    public void validatePalletCode() {
        try {

            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.HouseKeepingDTO, getActivity());
            HouseKeepingDTO houseKeepingDTO = new HouseKeepingDTO();
            houseKeepingDTO.setCartonNo(scannedPallet);
            houseKeepingDTO.setWarehouseId(warehouseId);
            houseKeepingDTO.setAccountID(accountId);
            message.setEntityObject(houseKeepingDTO);


            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                ProgressDialogUtils.showProgressDialog("Please Wait");
                call = apiService.ValidateCartonForLiveStock(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;
                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "003_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);

                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;
                                for (int i = 0; i < _lExceptions.size(); i++) {

                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());
                                    ProgressDialogUtils.closeProgressDialog();

                                    scannedPallet = "";

                                    cvScanPallet.setCardBackgroundColor(getResources().getColor(R.color.white));
                                    ivScanPallet.setImageResource(R.drawable.warning_img);
                                    common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                                    return;

                                }

                            } else {
                                List<LinkedTreeMap<?, ?>> _lResult = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lResult = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                HouseKeepingDTO dto = null;
                                ProgressDialogUtils.closeProgressDialog();

                                for (int i = 0; i < _lResult.size(); i++) {

                                    dto = new HouseKeepingDTO(_lResult.get(i).entrySet());
                                    if (dto.getResult().equals("1")) {

                                        isPalletScanned = true;

                                        cvScanPallet.setCardBackgroundColor(getResources().getColor(R.color.white));
                                        ivScanPallet.setImageResource(R.drawable.check);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_02", getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ProgressDialogUtils.closeProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "003_02", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "003_03", getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
        }
    }


    public void validateMaterial() {
        try {

            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.Inventory, getActivity());
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setAccountID(accountId);
            inventoryDTO.setTenantCode(selectedTenant);
            inventoryDTO.setTenantID(tenantId);
            inventoryDTO.setWarehouseId(warehouseId);
            inventoryDTO.setWarehouse(selectedWH);
            inventoryDTO.setMaterialCode(scannedSKU);
            message.setEntityObject(inventoryDTO);


            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                ProgressDialogUtils.showProgressDialog("Please Wait");
                call = apiService.CheckTenatMaterial(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;
                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "003_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);

                        if ((core.getType().toString().equals("Exception"))) {
                            List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                            _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                            WMSExceptionMessage owmsExceptionMessage = null;
                            for (int i = 0; i < _lExceptions.size(); i++) {

                                owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());
                                ProgressDialogUtils.closeProgressDialog();
                                cvScanRSN.setCardBackgroundColor(getResources().getColor(R.color.white));
                                ivScanRSN.setImageResource(R.drawable.warning_img);
                                scannedSKU = "";
                                common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                                return;

                            }

                        } else {
                            List<LinkedTreeMap<?, ?>> _lResult = new ArrayList<LinkedTreeMap<?, ?>>();
                            _lResult = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                            InventoryDTO dto = null;
                            ProgressDialogUtils.closeProgressDialog();

                            for (int i = 0; i < _lResult.size(); i++) {

                                dto = new InventoryDTO(_lResult.get(i).entrySet());
                                if (dto.getResult().equals("1")) {

                                    isSKUScanned = true;

                                    cvScanRSN.setCardBackgroundColor(getResources().getColor(R.color.white));
                                    ivScanRSN.setImageResource(R.drawable.check);
                                } else {
                                    cvScanRSN.setCardBackgroundColor(getResources().getColor(R.color.white));
                                    ivScanRSN.setImageResource(R.drawable.invalid_cross);
                                    common.showUserDefinedAlertType(errorMessages.EMC_0022, getActivity(), getContext(), "Error");
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "003_02", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "003_03", getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
        }
    }

    public void GetActiveStockData() {
        try {


            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.Inventory, getContext());
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setMaterialCode(scannedSKU);
            inventoryDTO.setLocationCode(scannedLocation);
            inventoryDTO.setContainerCode(scannedPallet);
            inventoryDTO.setTenantCode(selectedTenant);
            inventoryDTO.setAccountID(accountId);
            inventoryDTO.setTenantCode(selectedTenant);
            inventoryDTO.setTenantID(tenantId);
            inventoryDTO.setWarehouseId(warehouseId);
            inventoryDTO.setWarehouse(selectedWH);
            inventoryDTO.setMaterialCode(scannedSKU);
            inventoryDTO.setBatchNo(batch);
            inventoryDTO.setSerialNo(serialNo);
            inventoryDTO.setMfgDate(mfg);
            inventoryDTO.setExpDate(exp);
            inventoryDTO.setProjectNo(prjRef);
            inventoryDTO.setMRP(mrp);
            message.setEntityObject(inventoryDTO);


            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                ProgressDialogUtils.showProgressDialog("Please Wait");
                call = apiService.GetActivestock(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;
                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                common.showUserDefinedAlertType(errorMessages.EMC_0002, getActivity(), getContext(), "Error");
            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() != null) {
                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);
                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;

                                for (int i = 0; i < _lExceptions.size(); i++) {

                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());


                                }

                                recyclerViewLiveStock.setAdapter(null);

                                ProgressDialogUtils.closeProgressDialog();
                                common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                            } else {


                                List<LinkedTreeMap<?, ?>> _lInventory = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lInventory = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();
                                List<InventoryDTO> lstInventory = new ArrayList<InventoryDTO>();

                                ProgressDialogUtils.closeProgressDialog();

                                if (_lInventory.size() > 0) {
                                    InventoryDTO inventorydto = null;
                                    for (int i = 0; i < _lInventory.size(); i++) {

                                        inventorydto = new InventoryDTO(_lInventory.get(i).entrySet());
                                        lstInventory.add(inventorydto);
                                    }

                                    LiveStockAdapter liveStockAdapter = new LiveStockAdapter(getActivity(), lstInventory);
                                    recyclerViewLiveStock.setAdapter(liveStockAdapter);
                                } else {
                                    recyclerViewLiveStock.setAdapter(null);
                                    common.showUserDefinedAlertType(errorMessages.EMC_0060, getActivity(), getContext(), "Warning");
                                    return;
                                }

                            }
                        } else {
                            recyclerViewLiveStock.setAdapter(null);
                            ProgressDialogUtils.closeProgressDialog();
                            common.showUserDefinedAlertType(errorMessages.EMC_0021, getActivity(), getContext(), "Error");

                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {

                        recyclerViewLiveStock.setAdapter(null);
                        ProgressDialogUtils.closeProgressDialog();
                        common.showUserDefinedAlertType(errorMessages.EMC_0001, getActivity(), getContext(), "Error");

                        return;
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_02", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                recyclerViewLiveStock.setAdapter(null);
                ProgressDialogUtils.closeProgressDialog();
                common.showUserDefinedAlertType(errorMessages.EMC_0001, getActivity(), getContext(), "Error");


            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "001_03", getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ProgressDialogUtils.closeProgressDialog();
            common.showUserDefinedAlertType(errorMessages.EMC_0003, getActivity(), getContext(), "Error");
            return;
        }
    }

    // sending exception to the database
    public void logException() {
        try {

            String textFromFile = exceptionLoggerUtils.readFromFile(getActivity());

            WMSCoreMessage message = new WMSCoreMessage();
            message = common.SetAuthentication(EndpointConstants.Exception, getActivity());
            WMSExceptionMessage wmsExceptionMessage = new WMSExceptionMessage();
            wmsExceptionMessage.setWMSMessage(textFromFile);
            message.setEntityObject(wmsExceptionMessage);

            Call<String> call = null;
            ApiInterface apiService =
                    RestService.getClient().create(ApiInterface.class);

            try {
                //Checking for Internet Connectivity
                // if (NetworkUtils.isInternetAvailable()) {
                // Calling the Interface method
                call = apiService.LogException(message);
                // } else {
                // DialogUtils.showAlertDialog(getActivity(), "Please enable internet");
                // return;
                // }

            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "002_01", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
            }
            try {
                //Getting response from the method
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            core = gson.fromJson(response.body().toString(), WMSCoreMessage.class);

                            // if any Exception throws
                            if ((core.getType().toString().equals("Exception"))) {
                                List<LinkedTreeMap<?, ?>> _lExceptions = new ArrayList<LinkedTreeMap<?, ?>>();
                                _lExceptions = (List<LinkedTreeMap<?, ?>>) core.getEntityObject();

                                WMSExceptionMessage owmsExceptionMessage = null;
                                for (int i = 0; i < _lExceptions.size(); i++) {
                                    owmsExceptionMessage = new WMSExceptionMessage(_lExceptions.get(i).entrySet());
                                    ProgressDialogUtils.closeProgressDialog();
                                    common.showAlertType(owmsExceptionMessage, getActivity(), getContext());
                                    return;
                                }
                            } else {
                                LinkedTreeMap<String, String> _lResultvalue = new LinkedTreeMap<String, String>();
                                _lResultvalue = (LinkedTreeMap<String, String>) core.getEntityObject();
                                for (Map.Entry<String, String> entry : _lResultvalue.entrySet()) {
                                    if (entry.getKey().equals("Result")) {
                                        String Result = entry.getValue();
                                        if (Result.equals("0")) {
                                            ProgressDialogUtils.closeProgressDialog();
                                            return;
                                        } else {
                                            ProgressDialogUtils.closeProgressDialog();
                                            exceptionLoggerUtils.deleteFile(getActivity());
                                            return;
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {

                            try {
                                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "002_02", getActivity());
                                logException();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ProgressDialogUtils.closeProgressDialog();

                            //Log.d("Message", core.getEntityObject().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        //Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_LONG).show();
                        ProgressDialogUtils.closeProgressDialog();
                        DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0001);
                    }
                });
            } catch (Exception ex) {
                try {
                    exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "002_03", getActivity());
                    logException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgressDialogUtils.closeProgressDialog();
                DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0002);
            }
        } catch (Exception ex) {
            try {
                exceptionLoggerUtils.createExceptionLog(ex.toString(), classCode, "002_04", getActivity());
                logException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressDialogUtils.closeProgressDialog();
            DialogUtils.showAlertDialog(getActivity(), errorMessages.EMC_0003);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
            barcodeReader.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                // Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
            }
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_activity_live_stock));
    }

    @Override
    public void onDestroyView() {

        // Honeywell onDestroyView
        if (barcodeReader != null) {
            // unregister barcode event listener honeywell
            barcodeReader.removeBarcodeListener((BarcodeReader.BarcodeListener) this);

            // unregister trigger state change listener
            barcodeReader.removeTriggerListener((BarcodeReader.TriggerListener) this);
        }

        // Cipher onDestroyView
        Intent RTintent = new Intent("sw.reader.decode.require");
        RTintent.putExtra("Enable", false);
        getActivity().sendBroadcast(RTintent);
        getActivity().unregisterReceiver(this.myDataReceiver);
        super.onDestroyView();

    }

}