package com.ntam.tech.metamac.activity;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.adapter.AgendaDayAdapter;
import com.ntam.tech.metamac.adapter.SessionAgendaAdapter;
import com.ntam.tech.metamac.adapter.SessionMyAgendaAdapter;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.interfaces.AgendaInterface;
import com.ntam.tech.metamac.interfaces.OnClickListenerAdapter;
import com.ntam.tech.metamac.model.Agenda;
import com.ntam.tech.metamac.model.Session;
import com.ntam.tech.metamac.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class AgendaActivity extends AppCompatActivity implements AgendaInterface {

    private Toolbar mToolbar;
    private TextView tvAgena;
    private TextView tvMyAgena;
    private ProgressBar progress;

    // adapter
    SessionAgendaAdapter sessionAgendaAdapter;
    SessionMyAgendaAdapter sessionMyAgendaAdapter;
    //AgendaDayAdapter agendaDayAdapter;
    List<Agenda> agendaList;
    List<Agenda> myAgendaList;
    TabLayout tabLayout;
    //Spinner spinner;

    RecyclerView recyclerView;
    boolean currentTabMyAgenda = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        findViewById();
        onClick();
        //agendaDayAdapter = new AgendaDayAdapter(this);
        loadAgenda();
    }

    private void onClick() {
        tvAgena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAgena.setTextColor(ContextCompat.getColor(AgendaActivity.this, R.color.header));
                tvAgena.setBackground(ContextCompat.getDrawable(AgendaActivity.this, R.drawable.borderwhite));

                tvMyAgena.setTextColor(ContextCompat.getColor(AgendaActivity.this, R.color.white));
                tvMyAgena.setBackgroundResource(android.R.color.transparent);
                currentTabMyAgenda = false;
                loadAgenda();

            }
        });
        tvMyAgena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMyAgena.setTextColor(ContextCompat.getColor(AgendaActivity.this, R.color.header));
                tvMyAgena.setBackground(ContextCompat.getDrawable(AgendaActivity.this, R.drawable.borderwhite));

                tvAgena.setTextColor(ContextCompat.getColor(AgendaActivity.this, R.color.white));
                tvAgena.setBackgroundResource(android.R.color.transparent);
                currentTabMyAgenda = true;
                loadMyAgenda();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (currentTabMyAgenda) {
                    if (myAgendaList != null) {
                        if (sessionMyAgendaAdapter == null)
                            sessionMyAgendaAdapter = new SessionMyAgendaAdapter(AgendaActivity.this);
                        sessionMyAgendaAdapter.setData(myAgendaList.get(tab.getPosition()).getSessions());
                        recyclerView.setAdapter(sessionMyAgendaAdapter);
                    }
                } else {
                    if (agendaList != null) {
                        if (sessionAgendaAdapter == null)
                            sessionAgendaAdapter = new SessionAgendaAdapter(AgendaActivity.this);
                        sessionAgendaAdapter.setData(agendaList.get(tab.getPosition()).getSessions());
                        recyclerView.setAdapter(sessionAgendaAdapter);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    /*    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (currentTabMyAgenda) {
                        if (myAgendaList != null) {
                            if (sessionMyAgendaAdapter == null)
                                sessionMyAgendaAdapter = new SessionMyAgendaAdapter(AgendaActivity.this);
                            sessionMyAgendaAdapter.setData(myAgendaList.get(position).getSessions());
                            recyclerView.setAdapter(sessionMyAgendaAdapter);
                        }
                    } else {
                        if (agendaList != null) {
                            if (sessionAgendaAdapter == null)
                                sessionAgendaAdapter = new SessionAgendaAdapter(AgendaActivity.this);
                            sessionAgendaAdapter.setData(agendaList.get(position).getSessions());
                            recyclerView.setAdapter(sessionAgendaAdapter);
                        }
                    }
                } catch (Exception e) {
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void selectDay(int day) {
        try {
            if (currentTabMyAgenda) {
                if (myAgendaList != null) {
                    if (sessionMyAgendaAdapter == null)
                        sessionMyAgendaAdapter = new SessionMyAgendaAdapter(AgendaActivity.this);
                    sessionMyAgendaAdapter.setData(myAgendaList.get(day).getSessions());
                    recyclerView.setAdapter(sessionMyAgendaAdapter);
                }
            } else {
                if (agendaList != null) {
                    if (sessionAgendaAdapter == null)
                        sessionAgendaAdapter = new SessionAgendaAdapter(AgendaActivity.this);
                    sessionAgendaAdapter.setData(agendaList.get(day).getSessions());
                    recyclerView.setAdapter(sessionAgendaAdapter);
                }
            }
        } catch (Exception e) {
            finish();
        }
    }

    private void findViewById() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //spinner = (Spinner) findViewById(R.id.spinner);
        progress = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        tvAgena = (TextView) findViewById(R.id.tv_agena);
        tvMyAgena = (TextView) findViewById(R.id.tv_my_agena);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadAgenda() {
        showData(false);
        RetrofitRequest.getAllAgenda(SharedPref.getMyAccount(this).getUserId(), new RetrofitResponse<List<com.ntam.tech.metamac.model.Agenda>>() {
            @Override
            public void onSuccess(List<Agenda> agendas) {
                agendaList = agendas;
                //ArrayList<String> days = new ArrayList<>();
                tabLayout.removeAllTabs();
                for (int i = 0; i < agendas.size(); i++) {
                    //days.add(item.getDayNumber() + "," + item.getEventDate());
                    //final TabItem b = new TabItem(AgendaActivity.this);
                    TabLayout.Tab item = tabLayout.newTab();
                    item.setText(agendas.get(i).getEventDate());
                    tabLayout.addTab(tabLayout.newTab().setText(agendas.get(i).getEventDate()));


                }
                //agendaDayAdapter.setDays(days);
               /* spinner.setAdapter(agendaDayAdapter);
                if(days.size()==1){
                    spinner.setEnabled(false);
                }else{
                    spinner.setEnabled(true);
                }*/
                //selectDay(0);
                showData(true);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(AgendaActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMyAgenda() {
        showData(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agendaList == null || agendaList.size() == 0)
                    return;
                myAgendaList = new ArrayList<>();
                for (Agenda currentAgenda : agendaList) {
                    ArrayList<Session> mySession = new ArrayList<>();
                    for (Session session : currentAgenda.getSessions()) {
                        if (session.isMyAgenda()) {
                            mySession.add(session);
                        }
                    }
                    if (mySession.size() > 0) {
                        Agenda agenda = new Agenda();
                        agenda.setDayNumber(currentAgenda.getDayNumber());
                        agenda.setEventDate(currentAgenda.getEventDate());
                        agenda.setSessions(mySession);
                        myAgendaList.add(agenda);
                    }
                }
                //final ArrayList<String> days = new ArrayList<>();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.removeAllTabs();
                        for (int i = 0; i < myAgendaList.size(); i++) {
                            //days.add(item.getDayNumber() + "," + item.getEventDate());
                            TabLayout.Tab item = tabLayout.newTab();
                            item.setText(myAgendaList.get(i).getEventDate());
                            tabLayout.addTab(tabLayout.newTab().setText(myAgendaList.get(i).getEventDate()));

                        }
                        //agendaDayAdapter.setDays(days);
                       /* spinner.setAdapter(agendaDayAdapter);
                        if(days.size()==1){
                            spinner.setEnabled(false);
                        }else{
                            spinner.setEnabled(true);
                        }*/
                        /*if (days.size() == 0) {
                            if (sessionMyAgendaAdapter == null)
                                sessionMyAgendaAdapter = new SessionMyAgendaAdapter(AgendaActivity.this);
                            sessionMyAgendaAdapter.setData(new ArrayList<Session>());
                            recyclerView.setAdapter(sessionMyAgendaAdapter);
                            linearLayout.setVisibility(View.GONE);
                            Toast.makeText(AgendaActivity.this, "No Session in Your Agenda", Toast.LENGTH_SHORT).show();
                        } else {
                            linearLayout.setVisibility(View.VISIBLE);
                        }*/
                        if(myAgendaList.size()>0) {
                            //selectDay(0);
                            showData(true);
                        }else {
                            Toast.makeText(AgendaActivity.this, "No Session in Your Agenda", Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }).start();

    }

    private void showData(boolean show) {
        if (show) {
            progress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void addToMyAgenda(int position) {
        final int currentPosition = position;
        if(!currentTabMyAgenda) {
            Session session = agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().get(position);
            session.setisMyAgenda(true);
            agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().set(position, session);
            sessionAgendaAdapter.updateData(agendaList.get(tabLayout.getSelectedTabPosition()).getSessions());
            RetrofitRequest.addToMyAgenda(SharedPref.getMyAccount(AgendaActivity.this).getUserId(), session.getId(), new RetrofitResponse<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    Toast.makeText(AgendaActivity.this, R.string.session_added, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(String errorMessage) {
                    Session session = agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().get(currentPosition);
                    session.setisMyAgenda(false);
                    agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().set(currentPosition, session);
                    sessionAgendaAdapter.updateData(agendaList.get(tabLayout.getSelectedTabPosition()).getSessions());
                    Toast.makeText(AgendaActivity.this, R.string.session_added_error, Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public void removeToMyAgenda(int position) {
        final int currentPosition = position;
        if(!currentTabMyAgenda) {
            Session session = agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().get(position);
            session.setisMyAgenda(false);
            agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().set(position, session);
            sessionAgendaAdapter.updateData(agendaList.get(tabLayout.getSelectedTabPosition()).getSessions());
            RetrofitRequest.removeToMyAgenda(SharedPref.getMyAccount(AgendaActivity.this).getUserId(), session.getId(), new RetrofitResponse<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    Toast.makeText(AgendaActivity.this, R.string.session_removed, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(String errorMessage) {
                    Session session = agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().get(currentPosition);
                    session.setisMyAgenda(true);
                    agendaList.get(tabLayout.getSelectedTabPosition()).getSessions().set(currentPosition, session);
                    sessionAgendaAdapter.updateData(agendaList.get(tabLayout.getSelectedTabPosition()).getSessions());
                    Toast.makeText(AgendaActivity.this, R.string.session_added_error, Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
}
