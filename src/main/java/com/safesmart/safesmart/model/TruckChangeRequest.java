package com.safesmart.safesmart.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class TruckChangeRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer cents;

	private Integer nickels;

	private Integer dimes;

	private Integer quarters;

	private Integer den_1$;

	private Integer den_5$;

	private Integer den_10$;

	private Integer den_20$;

	private Integer den_50$;

	private Integer den_100$;

	private String type;
	
	private OrderStatus orderStatus;
 
    private UserInfo createdBy;

    private LocalDateTime created;

    private UserInfo modifiedBy;

    private LocalDateTime modified;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCents() {
		return cents;
	}

	public void setCents(Integer cents) {
		this.cents = cents;
	}

	public Integer getNickels() {
		return nickels;
	}

	public void setNickels(Integer nickels) {
		this.nickels = nickels;
	}

	public Integer getDimes() {
		return dimes;
	}

	public void setDimes(Integer dimes) {
		this.dimes = dimes;
	}

	public Integer getQuarters() {
		return quarters;
	}

	public void setQuarters(Integer quarters) {
		this.quarters = quarters;
	}

	public Integer getDen_1$() {
		return den_1$;
	}

	public void setDen_1$(Integer den_1$) {
		this.den_1$ = den_1$;
	}

	public Integer getDen_5$() {
		return den_5$;
	}

	public void setDen_5$(Integer den_5$) {
		this.den_5$ = den_5$;
	}

	public Integer getDen_10$() {
		return den_10$;
	}

	public void setDen_10$(Integer den_10$) {
		this.den_10$ = den_10$;
	}

	public Integer getDen_20$() {
		return den_20$;
	}

	public void setDen_20$(Integer den_20$) {
		this.den_20$ = den_20$;
	}

	public Integer getDen_50$() {
		return den_50$;
	}

	public void setDen_50$(Integer den_50$) {
		this.den_50$ = den_50$;
	}

	public Integer getDen_100$() {
		return den_100$;
	}

	public void setDen_100$(Integer den_100$) {
		this.den_100$ = den_100$;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@OneToOne(cascade = CascadeType.MERGE)
	public UserInfo getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserInfo modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}
