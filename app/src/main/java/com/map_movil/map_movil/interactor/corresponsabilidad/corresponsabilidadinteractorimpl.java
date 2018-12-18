package com.map_movil.map_movil.interactor.corresponsabilidad;

import android.content.Context;
import com.map_movil.map_movil.presenter.corresponsabilidad.corresponsabilidadpresenter;
import com.map_movil.map_movil.repository.corresponsabilidad.corresponsabiliadrepositoryImpl;
import com.map_movil.map_movil.repository.corresponsabilidad.corresponsabilidadrepository;

public class corresponsabilidadinteractorimpl implements corresponsabilidadinteractor {

    private corresponsabilidadpresenter corresponsabilidadpresenter;
    private corresponsabiliadrepositoryImpl corresponsabilidadrepositoryimpl;

    public corresponsabilidadinteractorimpl(Context context , corresponsabilidadpresenter correspres ){
        this.corresponsabilidadpresenter = correspres;
        this.corresponsabilidadrepositoryimpl = new corresponsabilidadrepository(context , this.corresponsabilidadpresenter);
    }

    @Override
    public void findCorrByTitular(String strCodIdentidad) {
        this.corresponsabilidadrepositoryimpl.findCorrByTitular(strCodIdentidad);
    }
}
