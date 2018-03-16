package com.example.edu.termproject1;

        import android.content.Context;
        import android.util.SparseBooleanArray;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.Filter;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;

        import java.util.ArrayList;
        import java.util.List;

        import static java.lang.Character.isDigit;
        import static java.util.Collections.addAll;

/**
 * Created by EDU on 4/3/2017.
 */
public class ArrayAdapterPending extends BaseAdapter {
    Context context;
    List<String> pending;
    int remove;

    public ArrayAdapterPending(Context c, List<String> w) {
        context = c;
        pending = w;
    }

    @Override
    public int getCount() {
        if(pending!=null) {
            return pending.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public String getItem(int position) {
        return pending.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View row;
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserId = mDatabase.child(user_id);
        remove=0;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.array_adapter_pending, null);

        String uName =pending.get(position);
        if(!isDigit(uName.charAt(0))) {
            uName = uName.substring(0, 1).toUpperCase() + uName.substring(1);
        }

        TextView pendingName = (TextView) row.findViewById(R.id.pendingName);
        pendingName.setText(uName);

        Button accept=(Button)row.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Friend Added", Toast.LENGTH_SHORT).show();
                currentUserId.child("friends").push().setValue(pending.get(position));
                pending.remove(position);
                currentUserId.child("pending").setValue(pending);
                notifyDataSetChanged();
            }
        });

        Button ignore=(Button)row.findViewById(R.id.ignore);
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Ignored", Toast.LENGTH_SHORT).show();
                pending.remove(position);
                currentUserId.child("pending").setValue(pending);
                notifyDataSetChanged();
            }
        });


        return row;
    }
}