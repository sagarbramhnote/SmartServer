package com.safesmart.safesmart.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class ChangeValetDenominations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer old_cents;

	private Integer new_cents;

	private Integer old_nickels;

	private Integer new_nickels;

	private Integer old_dimes;

	private Integer new_dimes;

	private Integer old_quarters;

	private Integer new_quarters;

	private Integer old_den_1$;

	private Integer new_den_1$;

	private Integer old_den_5$;

	private Integer new_den_5$;

	private Integer old_den_10$;

	private Integer new_den_10$;

	private Integer old_den_20$;

	private Integer new_den_20$;

	private Integer old_den_50$;

	private Integer new_den_50$;

	private Integer old_den_100$;

	private Integer new_den_100$;

	private String type;

	private LocalDateTime updatedOn;

	private UserInfo updatedBy;

	private ValetDenominations valetDenominations;
	
	private UserInfo createdBy;

	private LocalDateTime created;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOld_cents() {
		return old_cents;
	}

	public void setOld_cents(Integer old_cents) {
		this.old_cents = old_cents;
	}

	public Integer getNew_cents() {
		return new_cents;
	}

	public void setNew_cents(Integer new_cents) {
		this.new_cents = new_cents;
	}

	public Integer getOld_nickels() {
		return old_nickels;
	}

	public void setOld_nickels(Integer old_nickels) {
		this.old_nickels = old_nickels;
	}

	public Integer getNew_nickels() {
		return new_nickels;
	}

	public void setNew_nickels(Integer new_nickels) {
		this.new_nickels = new_nickels;
	}

	public Integer getOld_dimes() {
		return old_dimes;
	}

	public void setOld_dimes(Integer old_dimes) {
		this.old_dimes = old_dimes;
	}

	public Integer getNew_dimes() {
		return new_dimes;
	}

	public void setNew_dimes(Integer new_dimes) {
		this.new_dimes = new_dimes;
	}

	public Integer getOld_quarters() {
		return old_quarters;
	}

	public void setOld_quarters(Integer old_quarters) {
		this.old_quarters = old_quarters;
	}

	public Integer getNew_quarters() {
		return new_quarters;
	}

	public void setNew_quarters(Integer new_quarters) {
		this.new_quarters = new_quarters;
	}

	public Integer getOld_den_1$() {
		return old_den_1$;
	}

	public void setOld_den_1$(Integer old_den_1$) {
		this.old_den_1$ = old_den_1$;
	}

	public Integer getNew_den_1$() {
		return new_den_1$;
	}

	public void setNew_den_1$(Integer new_den_1$) {
		this.new_den_1$ = new_den_1$;
	}

	public Integer getOld_den_5$() {
		return old_den_5$;
	}

	public void setOld_den_5$(Integer old_den_5$) {
		this.old_den_5$ = old_den_5$;
	}

	public Integer getNew_den_5$() {
		return new_den_5$;
	}

	public void setNew_den_5$(Integer new_den_5$) {
		this.new_den_5$ = new_den_5$;
	}

	public Integer getOld_den_10$() {
		return old_den_10$;
	}

	public void setOld_den_10$(Integer old_den_10$) {
		this.old_den_10$ = old_den_10$;
	}

	public Integer getNew_den_10$() {
		return new_den_10$;
	}

	public void setNew_den_10$(Integer new_den_10$) {
		this.new_den_10$ = new_den_10$;
	}

	public Integer getOld_den_20$() {
		return old_den_20$;
	}

	public void setOld_den_20$(Integer old_den_20$) {
		this.old_den_20$ = old_den_20$;
	}

	public Integer getNew_den_20$() {
		return new_den_20$;
	}

	public void setNew_den_20$(Integer new_den_20$) {
		this.new_den_20$ = new_den_20$;
	}

	public Integer getOld_den_50$() {
		return old_den_50$;
	}

	public void setOld_den_50$(Integer old_den_50$) {
		this.old_den_50$ = old_den_50$;
	}

	public Integer getNew_den_50$() {
		return new_den_50$;
	}

	public void setNew_den_50$(Integer new_den_50$) {
		this.new_den_50$ = new_den_50$;
	}

	public Integer getOld_den_100$() {
		return old_den_100$;
	}

	public void setOld_den_100$(Integer old_den_100$) {
		this.old_den_100$ = old_den_100$;
	}

	public Integer getNew_den_100$() {
		return new_den_100$;
	}

	public void setNew_den_100$(Integer new_den_100$) {
		this.new_den_100$ = new_den_100$;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	@ManyToOne
	public ValetDenominations getValetDenominations() {
		return valetDenominations;
	}

	@JsonProperty
	public void setValetDenominations(ValetDenominations valetDenominations) {
		this.valetDenominations = valetDenominations;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	@OneToOne(cascade = CascadeType.MERGE)
	public UserInfo getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserInfo updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToOne(cascade = CascadeType.MERGE)
	public UserInfo getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserInfo createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
}
