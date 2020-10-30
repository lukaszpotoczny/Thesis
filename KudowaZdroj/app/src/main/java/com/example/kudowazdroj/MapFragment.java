package com.example.kudowazdroj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapFragment extends Fragment {


    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7;
    Button button;
   // private Firebase mRef;
   private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.map_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_4);

        editText1 = root.findViewById(R.id.edit1);
        editText2 = root.findViewById(R.id.edit2);
        editText3 = root.findViewById(R.id.edit3);
        editText4 = root.findViewById(R.id.edit4);
        editText5 = root.findViewById(R.id.edit5);
        editText6 = root.findViewById(R.id.edit6);
        editText7 = root.findViewById(R.id.edit7);
        button = root.findViewById(R.id.add_stuff);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Accommodation");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 int id = Integer.valueOf(editText1.getText().toString());
                 String name = editText2.getText().toString();
                 String address = editText3.getText().toString();
                 String rating = editText4.getText().toString();
                 String phone = editText5.getText().toString();
                 String website = editText6.getText().toString();
                 String content = editText7.getText().toString();
                 Rest rest = new Rest(id, name, address, rating, phone, website,content);

                 reference.child(name).setValue(rest);
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

        public Rest(){
        }

        public Rest(int id, String n, String a, String r, String p, String w, String c){
            this.id = id;
            this.name = n;
            this.address = a;
            this.rating = r;
            this.phone = p;
            this.website = w;
            this.content = c;
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
    }

}
