package br.projecao.fabricadesoftware.disponibilidadeprofessoresapi.dominio;

public enum NivelAcesso {
	ADMINISTRADOR(1, "ROLE_ADMIN"),
	DIRETOR(2, "ROLE_DIRETOR"),
	COORDENADOR(3, "ROLE_COORDENADOR"),
	PROFESSOR(4, "ROLE_PROFESSOR");
	
	private int codigo;
	private String role;
	
	private NivelAcesso(int codigo, String role) {
		this.codigo=codigo;
		this.role=role;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public boolean equals(NivelAcesso nivel) {
		return this.codigo == nivel.codigo;
	}
	public boolean equals(String role) {
		return this.role.equals(role);
	}
	
	public NivelAcesso getNivelAcesso(int codigo) {
		switch (codigo) {
		case 1:
			return ADMINISTRADOR;
		case 2:
			return DIRETOR;
		case 3:
			return COORDENADOR;
		case 4:
			return PROFESSOR;
		default:
			return null;
		}
	}
	
	public NivelAcesso getNivelAcesso(String role) {
		switch (role) {
		case "ROLE_ADMIN":
			return ADMINISTRADOR;
		case "ROLE_DIRETOR":
			return DIRETOR;
		case "ROLE_COORDENADOR":
			return COORDENADOR;
		case "ROLE_PROFESSOR":
			return PROFESSOR;
		default:
			return null;
		}
	}
}
