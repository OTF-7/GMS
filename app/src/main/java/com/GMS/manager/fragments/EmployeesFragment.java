package com.GMS.manager.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.Constant;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.databinding.FragmentEmployeesBinding;
import com.GMS.login.activities.LoginActivity;
import com.GMS.manager.adapters.EmployeesAdapter;
import com.GMS.manager.helperClasses.ItemClickListener;
import com.GMS.manager.models.Employees;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EmployeesFragment extends Fragment {
    private static final String TAG = EmployeesFragment.class.toString();
    public static int pendingNotification = 0;
    FragmentEmployeesBinding mEmployeesBinding;
    EmployeesAdapter employeesAdapter;
    List<Employees> mEmployeesList;
    Employees employee;
    MenuItem mMenuItemNotification;
    TextView tvNotificationCounter;
    ImageView ivNotificationIcon;
    NavController mNavController;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private ItemClickListener mItemClickListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mEmployeesBinding = FragmentEmployeesBinding.inflate(inflater, container, false);
        mEmployeesList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        employeesAdapter = new EmployeesAdapter(mEmployeesList, mItemClickListener);
        mEmployeesBinding.employeesRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
        mEmployeesBinding.employeesRecyclerView.setAdapter(employeesAdapter);
        mEmployeesBinding.employeesRecyclerView.setLayoutManager(layoutManager);
        mEmployeesBinding.employeesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    requireActivity().findViewById(R.id.manager_add_floating_action_button)
                            .animate()
                            .alpha(0);

                    requireActivity().findViewById(R.id.manager_bottomAppBar)
                            .animate()
                            .translationY(requireActivity().findViewById(R.id.manager_bottomAppBar).getHeight())
                            .alpha(0);
                } else {
                    requireActivity().findViewById(R.id.manager_add_floating_action_button)
                            .animate()
                            .alpha(1);

                    requireActivity().findViewById(R.id.manager_bottomAppBar)
                            .animate()
                            .translationY(0)
                            .alpha(1);
                }
            }
        });
        return mEmployeesBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFirestore = FirebaseFirestore.getInstance();
        fillEmployees();
    }

    private void fillEmployees() {
        mItemClickListener = position -> {
            @NonNull NavDirections navigationAction = EmployeesFragmentDirections.actionEmployeeFragmentToEmployeeDetailsFragment(mEmployeesList.get(position), mEmployeesList.get(position).getEmployeeFirstName());
            mNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
            mNavController.navigate(navigationAction);
        };
        CollectionReference employees_collection = mFirestore.collection(Constant.Employees.toString());
        employees_collection.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.d("TAG", "Error getting documents: ", error);
                return;
            }

            assert value != null;
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.REMOVED) {
                    mEmployeesList.remove(documentChange.getOldIndex());
                    employeesAdapter.notifyItemRemoved(documentChange.getOldIndex());
                    continue;
                }
                employee = new Employees();
                DocumentSnapshot document = documentChange.getDocument();
                String employeeName = document.getString("Name");
                int employeeIcon = R.drawable.sign_in_image;
                String employeeType = document.getString("Type_of_jop");

                employee.setEmployeeFirstName(employeeName);
                employee.setEmployeeImage(employeeIcon);
                employee.setEmployeeJopType(employeeType);
                switch (documentChange.getType()) {
                    case ADDED:
                        mEmployeesList.add(employee);
                        employeesAdapter.notifyItemInserted(documentChange.getNewIndex());
                        break;
                    case MODIFIED:
                        mEmployeesList.set(documentChange.getOldIndex(), employee);
                        employeesAdapter.notifyItemChanged(documentChange.getNewIndex());
                        break;
                }
                Toast.makeText(getContext(), String.valueOf(mEmployeesList.size()), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onComplete: " + mEmployeesList.size());
            }
            Log.d("TAG", mEmployeesList.toString());
        });
    }


    private void checkNotification() {
        mMenuItemNotification.setActionView(R.layout.notification_layout);
        View view = mMenuItemNotification.getActionView();
        tvNotificationCounter = view.findViewById(R.id.notification_counter);
        ivNotificationIcon = view.findViewById(R.id.iv_notification_icon);
        ivNotificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @NonNull NavDirections action = EmployeesFragmentDirections.actionEmployeeFragmentToSignUpRequestsFragment();
                mNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                mNavController.navigate(action);
            }
        });
        if (pendingNotification > 0) {
            Drawable mDrawable = requireActivity().getDrawable(R.drawable.notification_counter_shape);
            view.findViewById(R.id.card_view).setBackground(mDrawable);
            tvNotificationCounter.setText(String.valueOf(pendingNotification));
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manager_top_bar, menu);
        mMenuItemNotification = menu.findItem(R.id.menu_manager_item_notification);
        checkNotification();
        MenuItem searchItem = menu.findItem(R.id.menu_manager_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                employeesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employeesAdapter.getFilter().filter(newText);
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_manager_item_setting:
                startActivity(new Intent(requireContext(), SettingActivity.class));
                break;

            case R.id.menu_manager_item_log_out:
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                requireActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}