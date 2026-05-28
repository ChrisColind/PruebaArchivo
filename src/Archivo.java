/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rogelio
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class Archivo{
 
    public int txt;
    public int java;
    public int pdf;
    public int otros;
 
    public Archivo(){
        limpiar();
    }
 
    public void limpiar(){
        txt= 0;
        java= 0;
        pdf= 0;
        otros = 0;
    }
 
    public boolean esValido(String ruta){
        if(ruta == null || ruta.trim().isEmpty()){
            return false;
        }
        File dir = new File(ruta.trim());
        return dir.exists() && dir.isDirectory();
    }
 
    public void contarExten(File dir){
        File[] lista = dir.listFiles();
        if(lista == null){
            return;
        }
        for(File x : lista){
            if(x.isDirectory()){
                contarExten(x);
            }else{
                clasificar(x);
            }
        }
    }
 
    private void clasificar(File f){
        String nombre = f.getName().toLowerCase();
        if(nombre.endsWith(".txt")){
            txt++;
        } else if(nombre.endsWith(".java")){
            java++;
        } else if(nombre.endsWith(".pdf")){
            pdf++;
        } else{
            otros++;
        }
    }
 
    public List<String> buscar(File dir, String busqueda){
        List<String> resultados = new ArrayList<>();
        BuscarArchivo(dir, busqueda.toLowerCase(), resultados);
        return resultados;
    }
 
    private void BuscarArchivo(File dir, String busqueda, List<String> resultados){
        File[] lista = dir.listFiles();
        if(lista == null){
            return;
        }
        for(File f : lista){
            if(f.isDirectory()){
                BuscarArchivo(f, busqueda, resultados);
            }else{
                if(f.getName().toLowerCase().contains(busqueda)){
                    resultados.add(f.getAbsolutePath());
                }
            }
        }
    }
 
    public int getTxt(){ 
        return txt; 
    }
    public int getJava(){ 
        return java; 
    }
    public int getPdf(){ 
        return pdf; 
    }
    public int getOtros(){ 
        return otros; 
    }
}
 