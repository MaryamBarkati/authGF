package tn.oga.wego.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.sun.istack.NotNull;


@Entity
public class User {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @NotNull
	    @Column(unique = true)
	     private String first_name;
	    
	    @NotNull
	    @Column(unique = true)
	     private String last_name;
	    
	    
	    
	    
	    public User() {
		}
	    
	    

		public User(String email, String password) {
			super();
			this.email = email;
			this.password = password;
		}



		public User(int id, String first_name, String last_name, String email, String password, Set<Rol> roles) {
			super();
			this.id = id;
			this.first_name = first_name;
			this.last_name = last_name;
			this.email = email;
			this.password = password;
			this.roles = roles;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirst_name() {
			return first_name;
		}

		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}

		public String getLast_name() {
			return last_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Set<Rol> getRoles() {
			return roles;
		}

		public void setRoles(Set<Rol> roles) {
			this.roles = roles;
		}

		@NotNull
	    @Column(unique = true)
	     private String email;
	    
	    @NotNull
	    private String password;
	    
	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id" ),
	    inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	    private Set<Rol> roles = new HashSet<>();


}
