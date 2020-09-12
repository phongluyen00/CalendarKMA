package com.example.retrofitrxjava.persional;

public class PersonalPresenter implements PersonalContract.Presenter {

    private PersonalContract.View view;

    public PersonalPresenter(PersonalContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveScore(String id) {
    }
}
