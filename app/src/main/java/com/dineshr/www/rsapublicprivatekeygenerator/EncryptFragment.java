package com.dineshr.www.rsapublicprivatekeygenerator;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;



public class EncryptFragment extends Fragment {
    Context mContext;

    private static final String ALGORITHM = "RSA";
    View vi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (vi == null) {
            vi = inflater.inflate(R.layout.encrypt_layout, container, false);
        }
        return vi;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        {
            super.onActivityCreated(savedInstanceState);
            Button en, encpy;
            final EditText edpk, edmsg;

            final TextView tvp;
            this.mContext = mContext;
            //View view = inflater.inflate(R.layout.key_layout, container, false);
            View v = getView();


            en = (Button) v.findViewById(R.id.button3);
            encpy = (Button) v.findViewById(R.id.button2);
            //cpr = (Button) v.findViewById(R.id.button_prk);
            tvp = (TextView) v.findViewById(R.id.textView4);
            edpk = (EditText) v.findViewById(R.id.editText);
            edmsg = (EditText) v.findViewById(R.id.editText2);
            //tvpr = (TextView) v.findViewById(R.id.textView_pr);
            //final String finalPk = pk;
            //final String finalPrk = prk;
            en.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(TextUtils.isEmpty(edpk.getText().toString())){ //&& TextUtils.isEmpty(edmsg.getText().toString()) )) {
                        edpk.setError("Enter Recepients Public key");
                        return;
                    }else if(TextUtils.isEmpty(edmsg.getText().toString())){
                        edmsg.setError("Enter Msg to encrypted (107 characters)");
                        return;
                    }else {


                        //KeyPair gkp;
                        //String pk = null, prk = null;
                        try {
                            edpk.getText().toString();
                            edmsg.getText().toString();
                            byte[] encryptedData = encrypt(hexStringToByteArray(edpk.getText().toString()), edmsg.getText().toString().getBytes());
                            String emsg = byteArrayToHexString(encryptedData);
                            tvp.setText(emsg);
                        } catch (NoSuchAlgorithmException e) {
                            Log.d("e", "NoSuchAlgorithmException");
                        } catch (NoSuchProviderException e) {
                            Log.d("e", "NoSuchProviderException");
                        } catch (InvalidKeySpecException e) {
                            Log.d("e", "InvalidKeySpecException");
                        } catch (Exception e) {
                            Log.d("e", "Exception");
                        }
                    }
                    //Log.d("msg"," outside button pressed"+pk);
                }
            });

            encpy.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (tvp.getText().toString().length()!=0) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", tvp.getText().toString());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getActivity(), "Msg copied!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "No Msg to copy!", Toast.LENGTH_LONG).show();
                    }
                }
            });



            //
        }
    }


    //encrypt code
    public static byte[] encrypt(byte[] publicKey, byte[] inputData) throws Exception {

        PublicKey key = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(publicKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PUBLIC_KEY, key);
        byte[] encryptedBytes = cipher.doFinal(inputData);
        return encryptedBytes;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }
}
