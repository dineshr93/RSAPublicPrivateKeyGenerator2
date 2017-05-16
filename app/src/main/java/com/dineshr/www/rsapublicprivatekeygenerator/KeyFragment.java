package com.dineshr.www.rsapublicprivatekeygenerator;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


public class KeyFragment extends Fragment {
    //GenerateKeyPair gkp;
    Context mContext;

    View vi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (vi == null) {
            vi = inflater.inflate(R.layout.key_layout, container, false);
        }
        return vi;
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button gk, cp, cpr;
        final TextView tvp, tvpr;
        this.mContext = mContext;
        //View view = inflater.inflate(R.layout.key_layout, container, false);
        View v = getView();
        gk = (Button) v.findViewById(R.id.button);
        cp = (Button) v.findViewById(R.id.button_pk);
        cpr = (Button) v.findViewById(R.id.button_prk);
        tvp = (TextView) v.findViewById(R.id.textView_p);
        tvpr = (TextView) v.findViewById(R.id.textView_pr);

        gk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                KeyPair gkp;
                String pk = null, prk = null;
                try {
                    // gkp = generateKeyPair();
                    KeyPair c;
                    c = generateKeyPair();
                    pk = getPublicKey(c);
                    prk = getPrivateKey(c);
                    // Log.d("e","inside button pressed"+pk);
                } catch (NoSuchAlgorithmException e) {
                    Log.d("e", "NoSuchAlgorithmException");
                } catch (NoSuchProviderException e) {
                    Log.d("e", "NoSuchProviderException");
                } catch (InvalidKeySpecException e) {
                    Log.d("e", "InvalidKeySpecException" + pk);
                }
                //Log.d("msg"," outside button pressed"+pk);
                tvp.setText(pk);
                tvpr.setText(prk);
            }
        });

        cp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tvp.getText().toString().length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", tvp.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getActivity(), "Public key copied!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No Msg to copy!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cpr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tvpr.getText().toString().length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", tvpr.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getActivity(), "Private key copied!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No Msg to copy!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static final String ALGORITHM = "RSA";
    KeyPair generateKeyPair;

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = null;
        KeyPair generateKeyPair = null;
        keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");//("SHA1PRNG" ,"SUN");
        //SecureRandom.getInstance("SHA1PRNG");
        keyGen.initialize(1024, random);// 512 is keysize
        generateKeyPair = keyGen.generateKeyPair();
        return generateKeyPair;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    public String getPublicKey(KeyPair x) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {

        byte[] publicKey = x.getPublic().getEncoded();
        String pb4 = byteArrayToHexString(publicKey);
        return pb4;
    }

    public String getPrivateKey(KeyPair y) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        //KeyPair generateKeyPair = generateKeyPair();
        byte[] privateKey = y.getPrivate().getEncoded();
        String pr4 = byteArrayToHexString(privateKey);
        return pr4;
    }
}
