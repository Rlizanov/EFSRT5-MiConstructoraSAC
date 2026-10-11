package com.edu.pe.service;            
                                       
import java.util.List;                 
import com.edu.pe.modelo.Empleado;     
                                       
public interface IEmpleadoService {    
                                       
    List<Empleado> ListarTodos();      
    boolean Guardar(Empleado empleado);
    Empleado BuscarPorId(int id);      
    boolean Eliminar(int id);          
}                                      