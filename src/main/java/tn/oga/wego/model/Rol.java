package tn.oga.wego.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import tn.oga.wego.enums.RolNombre;


@Entity
public class Rol {

	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    @Enumerated(EnumType.STRING)
	    @NotNull
	    @Column(unique = true)
	    private RolNombre rolNombre;
	    
	    
	    public Rol() {
	    }

	    public Rol(RolNombre rolNombre) {
	        this.rolNombre = rolNombre;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public RolNombre getRolNombre() {
	        return rolNombre;
	    }

	    public void setRolNombre(RolNombre rolNombre) {
	        this.rolNombre = rolNombre;
	    }
	}


