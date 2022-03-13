/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.pojos;


import java.io.Serializable;

import ru.chsergeig.shoppy.jooq.enums.Status;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String  login;
    private String  password;
    private Boolean salted;
    private Status  status;

    public Accounts() {}

    public Accounts(Accounts value) {
        this.id = value.id;
        this.login = value.login;
        this.password = value.password;
        this.salted = value.salted;
        this.status = value.status;
    }

    public Accounts(
        Integer id,
        String  login,
        String  password,
        Boolean salted,
        Status  status
    ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.salted = salted;
        this.status = status;
    }

    /**
     * Getter for <code>public.accounts.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.accounts.id</code>.
     */
    public Accounts setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>public.accounts.login</code>.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Setter for <code>public.accounts.login</code>.
     */
    public Accounts setLogin(String login) {
        this.login = login;
        return this;
    }

    /**
     * Getter for <code>public.accounts.password</code>.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>public.accounts.password</code>.
     */
    public Accounts setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Getter for <code>public.accounts.salted</code>.
     */
    public Boolean getSalted() {
        return this.salted;
    }

    /**
     * Setter for <code>public.accounts.salted</code>.
     */
    public Accounts setSalted(Boolean salted) {
        this.salted = salted;
        return this;
    }

    /**
     * Getter for <code>public.accounts.status</code>.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Setter for <code>public.accounts.status</code>.
     */
    public Accounts setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Accounts (");

        sb.append(id);
        sb.append(", ").append(login);
        sb.append(", ").append(password);
        sb.append(", ").append(salted);
        sb.append(", ").append(status);

        sb.append(")");
        return sb.toString();
    }
}
