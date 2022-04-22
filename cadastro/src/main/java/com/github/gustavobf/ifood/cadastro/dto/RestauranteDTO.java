package com.github.gustavobf.ifood.cadastro.dto;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gustavobf.ifood.cadastro.Restaurante;
import com.github.gustavobf.ifood.cadastro.infra.DTO;
import com.github.gustavobf.ifood.cadastro.infra.ValidDTO;

import io.smallrye.common.constraint.NotNull;

@ValidDTO
public class RestauranteDTO implements DTO {

	public Long id;

	@NotEmpty
	@NotNull
	public String proprietario;

	@Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
	@NotNull
	public String cnpj;

	@Size(min = 3, max = 30)
	public String nome;

	public LocalizacaoDTO localizacao;

	@JsonIgnore
	public String dataCriacao;

	public RestauranteDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalizacaoDTO getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(LocalizacaoDTO localizacao) {
		this.localizacao = localizacao;

	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();
		if (Restaurante.find("cnpj", cnpj).count() > 0) {
			constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
					.addPropertyNode("cnpj").addConstraintViolation();
			return false;
		}
		return true;
	}

}
