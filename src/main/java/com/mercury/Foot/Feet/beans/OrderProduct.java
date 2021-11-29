package com.mercury.Foot.Feet.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="order_product")
public class OrderProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private int userId;
	@Column
	private int qty;

	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.DETACH)
	private Order order;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.DETACH)
	private Product product;

	public OrderProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderProduct(int id, int userId, int qty, Order order, Product product) {
		super();
		this.id = id;
		this.userId = userId;
		this.qty = qty;
		this.order = order;
		this.product = product;
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", userId=" + userId + ", qty=" + qty + ", order=" + order + ", product="
				+ product + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@JsonIgnore
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	
	
	
	
}
