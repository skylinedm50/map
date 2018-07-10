package com.map_movil.map_movil.interactor.login;

import com.map_movil.map_movil.presenter.login.ChangePasswordPresenter;
import com.map_movil.map_movil.repository.login.ChangePasswordRepository;
import com.map_movil.map_movil.repository.login.ChangePasswordRepositoryImpl;

public class ChangePasswordInteractorImpl implements ChangePasswordInteractor {
    private ChangePasswordPresenter changePasswordPresenter;
    private ChangePasswordRepository changePasswordRepository;

    public ChangePasswordInteractorImpl(ChangePasswordPresenter changePasswordPresenter){
        this.changePasswordPresenter = changePasswordPresenter;
        changePasswordRepository = new ChangePasswordRepositoryImpl(this.changePasswordPresenter);
    }

    @Override
    public void setChangePassword(int intCodUser, String strNewPassword) {
        changePasswordRepository.setChangePassword(intCodUser, strNewPassword);
    }
}
