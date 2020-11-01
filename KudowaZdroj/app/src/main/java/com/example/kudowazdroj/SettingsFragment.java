package com.example.kudowazdroj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends Fragment {

    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8;
    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_4);

                editText1 = root.findViewById(R.id.edit1);
        editText2 = root.findViewById(R.id.edit2);
        editText3 = root.findViewById(R.id.edit3);
        editText4 = root.findViewById(R.id.edit4);
        editText5 = root.findViewById(R.id.edit5);
        editText6 = root.findViewById(R.id.edit6);
        editText7 = root.findViewById(R.id.edit7);
        editText8 = root.findViewById(R.id.edit8);
        button = root.findViewById(R.id.do_stuff);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Accommodation");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                int id = Integer.valueOf(editText1.getText().toString());
                String name = editText2.getText().toString();
                String address = editText3.getText().toString();
                String rating = editText4.getText().toString();
                String phone = editText5.getText().toString();
                String website = editText6.getText().toString();
                String content = editText7.getText().toString();
                String image = editText8.getText().toString();
                Rest rest = new Rest(id, name, address, rating, phone, website,content, image, content);
*/

                //reference.child(name).setValue(rest);

                String a = "Verde Montana Wellness and Spa";
                a = a.replaceAll(" ", "+");
                Uri uri = Uri.parse("https://www.google.com/maps/place/Hotel+Verde+Montana+Wellness+%26+Spa/@50.4437799,16.2481353,17z/data=!3m1!4b1!4m9!3m8!1s0x470e6f38faf423c3:0x444371570b6d500c!5m3!1s2021-03-18!4m1!1i2!8m2!3d50.4437799!4d16.250324");
                uri = Uri.parse("https://www.google.com/maps/search/" + a);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               // intent.setData(Uri.parse("geo:19,70"));
               // Intent chooser = Intent.createChooser(intent, "Launch Maps");
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

        return root;
    }

    public class Rest{

        public int id;
        public String name;
        public String address;
        public String rating;
        public String phone;
        public String website;
        public String content;
        public String email;
        public String image;

        public Rest(){
        }

        public Rest(int id, String n, String a, String r, String p, String w, String c, String email, String im){
            this.id = id;
            this.name = n;
            this.address = a;
            this.rating = r;
            this.phone = p;
            this.website = w;
            this.content = c;
            this.email = email;
            this.image = im;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
