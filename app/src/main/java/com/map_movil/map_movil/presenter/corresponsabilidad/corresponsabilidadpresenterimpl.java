package com.map_movil.map_movil.presenter.corresponsabilidad;

import android.content.Context;
import com.map_movil.map_movil.interactor.corresponsabilidad.corresponsabilidadinteractor;
import com.map_movil.map_movil.interactor.corresponsabilidad.corresponsabilidadinteractorimpl;
import com.map_movil.map_movil.model.Corresponsabilidades;
import com.map_movil.map_movil.model.CorresponsabilidadesClearByMenor;
import com.map_movil.map_movil.view.corresponsabilidad.corresponsabilidadview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class corresponsabilidadpresenterimpl implements corresponsabilidadpresenter {

    private corresponsabilidadview corresponsabilidadview;
    private corresponsabilidadinteractor corresponsabilidadinteractor;
    private String NombreTitular;
    private String CodHogar;
    private ArrayList <CorresponsabilidadesClearByMenor> listShowCorresponsabilidad;


    public corresponsabilidadpresenterimpl(Context context , corresponsabilidadview corrview){
        this.corresponsabilidadview = corrview;
        this.corresponsabilidadinteractor = new corresponsabilidadinteractorimpl(context , this);
    }

    @Override
    public void findCorrByTitular(String strCodIdentidad) {
        this.corresponsabilidadinteractor.findCorrByTitular(strCodIdentidad);
    }

    @Override
    public void showdata(ArrayList<Corresponsabilidades> corresponsabilidades) {
        procesar_datos(corresponsabilidades);
        this.corresponsabilidadview.showdata(listShowCorresponsabilidad , NombreTitular , CodHogar);
    }

    @Override
    public void findDataShowMessage(String error, Boolean find) {
        this.corresponsabilidadview.findDataShowMessage(error , find);
    }

    private void procesar_datos(ArrayList<Corresponsabilidades> listCorresponsabilidad){

        List<String> persona = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<>();
        listShowCorresponsabilidad = new ArrayList<>();

        //eliminar per persona repetido
        for(Corresponsabilidades item: listCorresponsabilidad){
            if (persona.isEmpty()){
                persona.add(item.getPer_persona());
                NombreTitular=item.getStrTitular();
                CodHogar=item.getSrtHogar();
            }else{
                if(!persona.contains(item.getPer_persona())){
                    persona.add(item.getPer_persona());
                }
            }
        }

        //crear nuevo array sin data repetida
        int  contador;
        for(int x=0;x<persona.size();x++)
        {
            contador=0;
            Map<String, String> map = new HashMap<String, String>();
            for(Corresponsabilidades item: listCorresponsabilidad){
                String perPersona = persona.get(x);
                if(perPersona.equals(item.getPer_persona())){
                    String parcial = new String(item.getStrCorresponsabilidad().toString());
                    if (parcial.equals("ASISTENCIA I (PARCIAL I y PARCIAL II)")){
                        if (!map.containsKey("ASISTENCIA I (PARCIAL I y PARCIAL II)")){
                            if (!map.containsKey("YearRegistro")){
                                map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                map.put("EstadoParcialI",item.getStrEstadoCorr());
                                map.put("YearRegistro", item.getStrYear());
                            }
                            else if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                map.put("EstadoParcialIB",item.getStrEstadoCorr());
                            }
                            else{
                                map.put("ASISTENCIA IB (PARCIAL I y PARCIAL II)", item.getStrDiasfaltantes());
                                map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                map.put("YearRegistroB", item.getStrYear());
                            }
                        }
                        if (!map.containsKey("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)")){
                            if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                map.put("EstadoParcialI",item.getStrEstadoCorr());
                            }
                            else{
                                map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                map.put("YearRegistroB", item.getStrYear());
                            }
                        }
                    }

                    if (parcial.equals("ASISTENCIA II (PARCIAL III y PARCIAL IV)")){
                        if (!map.containsKey("ASISTENCIA II (PARCIAL III y PARCIAL IV)")){
                            if (!map.containsKey("YearRegistro")){
                                map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                map.put("EstadoParcialII",item.getStrEstadoCorr());
                                map.put("YearRegistro", item.getStrYear());
                            }
                            else if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                map.put("EstadoParcialII",item.getStrEstadoCorr());
                            }
                            else{
                                map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                            }
                        }
                        if (!map.containsKey("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)")){
                            if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                map.put("EstadoParcialII",item.getStrEstadoCorr());
                            }
                            else{
                                map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                map.put("YearRegistroB", item.getStrYear());
                            }
                        }
                    }

                    String Visitamedica = item.getStrVisita();
                    if(!Visitamedica.equals("NO APLICA")){
                        map.put("VisitaIngreso",item.getStrIngresoVIsita());
                        map.put("Visitafecha"+contador,item.getStrVisita());
                        map.put("VisitaCod"+contador,item.getStrCodVisita());
                        contador++;
                    }
                    if(!item.getStrMatricula().equals("NO APLICA")){
                        map.put("matricula", item.getStrMatricula());
                    }
                    map.put("PerPersona", item.getPer_persona().toString());
                    map.put("nombre", item.getStrNombre());
                    map.put("titular", item.getStrTitular());
                    map.put("identidad", item.getStrIdentidad());
                    map.put("edad", item.getStrEdad().toString());
                    map.put("sexo", item.getStrSexo());
                }
            }
            if (!map.containsKey("matricula")){
                map.put("matricula", "NO APLICA");
            }
            if (!map.containsKey("VisitaIngreso")){
                map.put("VisitaIngreso", "N/D");
            }
            if (!map.containsKey("ASISTENCIA I (PARCIAL I y PARCIAL II)")){
                map.put("ASISTENCIA I (PARCIAL I y PARCIAL II)", "ND");
                map.put("EstadoParcialI","N/D");
            }
            if (!map.containsKey("ASISTENCIA II (PARCIAL III y PARCIAL IV)")){
                map.put("ASISTENCIA II (PARCIAL III y PARCIAL IV)", "ND");
                map.put("EstadoParcialII","N/D");
            }

            if (!map.containsKey("ASISTENCIA IB (PARCIAL I y PARCIAL II)")){
                map.put("ASISTENCIA IB (PARCIAL I y PARCIAL II)", "ND");
                map.put("EstadoParcialIB","N/D");
            }
            if (!map.containsKey("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)")){
                map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", "ND");
                map.put("EstadoParcialIIB","N/D");
            }
            if (!map.containsKey("YearRegistro")){
                map.put("YearRegistro","N/D");
            }
            if (!map.containsKey("YearRegistroB")){
                map.put("YearRegistroB","N/D");
            }
            for(int i=0;i<6;i++){
                if (!map.containsKey("VisitaCod"+i)){
                    map.put("Visitafecha"+i,"N/D");
                    map.put("VisitaCod"+i,"N/D");
                }
            }
            data.add(x, map);
        }

        for (Map<String, String> entry : data) {
             listShowCorresponsabilidad.add(new CorresponsabilidadesClearByMenor(entry.get("PerPersona"),entry.get("nombre"),
                                        entry.get("identidad"),entry.get("edad"),entry.get("sexo"),entry.get("matricula"),
                                        entry.get("YearRegistro"),entry.get("VisitaIngreso"),entry.get("ASISTENCIA I (PARCIAL I y PARCIAL II)"),
                                        entry.get("ASISTENCIA II (PARCIAL III y PARCIAL IV)"), entry.get("EstadoParcialI"),
                                        entry.get("EstadoParcialII"), entry.get("Visitafecha0"), entry.get("VisitaCod0"),
                                        entry.get("Visitafecha1"), entry.get("VisitaCod1"), entry.get("Visitafecha2"),
                                        entry.get("VisitaCod2"), entry.get("Visitafecha3"), entry.get("VisitaCod3"),
                                        entry.get("Visitafecha4"), entry.get("VisitaCod4"), entry.get("Visitafecha5"),
                                        entry.get("VisitaCod5"), entry.get("ASISTENCIA IB (PARCIAL I y PARCIAL II)"),
                                        entry.get("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)"), entry.get("EstadoParcialIB"),
                                        entry.get("EstadoParcialIIB"), entry.get("YearRegistroB"))
             );
        }


    }


}
