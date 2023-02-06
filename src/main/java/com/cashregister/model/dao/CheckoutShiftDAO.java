package com.cashregister.model.dao;

import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.User;
import com.cashregister.model.entity.Warehouse;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CheckoutShiftDAO extends AbstractDAO<Long, CheckoutShift>{
    private static final String SELECT_ALL_CHECKOUT = "SELECT checkout.id, checkout.checkout_shift_date, checkout.closed, checkout.warehouse_id, checkout.user_id FROM cash_register.checkout_shift AS checkout;";
    private static final String SELECT_CHECKOUT_BY_ID = "SELECT checkout.id, checkout.checkout_shift_date, checkout.closed, checkout.warehouse_id, checkout.user_id FROM cash_register.checkout_shift AS checkout WHERE checkout.id = ?;";
    private static final String SELECT_OPEN_CHECKOUT_BY_USER = "SELECT checkout.id, checkout.checkout_shift_date, checkout.closed, checkout.warehouse_id, checkout.user_id FROM cash_register.checkout_shift AS checkout WHERE checkout.warehouse_id = ? AND checkout.user_id = ? AND checkout.closed = ?";
    private static final String CREATE_CHECKOUT = "INSERT INTO checkout_shift (checkout_shift_date, closed, warehouse_id, user_id) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_CHECKOUT = "UPDATE checkout_shift SET closed = ? WHERE id = ?;";

    @Override
    public List<CheckoutShift> findAll() {
        return null;
    }

    @Override
    public Optional<CheckoutShift> findById(Long id) {
        CheckoutShift checkoutShift = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_CHECKOUT_BY_ID);
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                checkoutShift = createCheckoutEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.ofNullable(checkoutShift);
    }

    @Override
    public int getRecordsCount() {
        return 0;
    }

    public Optional<CheckoutShift> findOpenedCheckoutByUser(Warehouse warehouse, User user) {
        CheckoutShift checkoutShift = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_OPEN_CHECKOUT_BY_USER);
            stmt.setLong(1, warehouse.getId());
            stmt.setLong(2, user.getId());
            stmt.setBoolean(3, false);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                checkoutShift = createCheckoutEntity(resultSet, warehouse, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.ofNullable(checkoutShift);
    }

    private CheckoutShift createCheckoutEntity(ResultSet resultSet, Warehouse warehouse, User user){
        CheckoutShift checkoutShift = new CheckoutShift(warehouse);
        checkoutShift.setUser(user);
        try {
            checkoutShift.setId(resultSet.getLong("id"));
            checkoutShift.setDate(resultSet.getTimestamp(TableFields.CHECKOUT_DATE));
            checkoutShift.setClosed(resultSet.getBoolean(TableFields.CHECKOUT_CLOSED));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkoutShift;
    }

    private CheckoutShift createCheckoutEntity(ResultSet resultSet){
        Warehouse warehouse;
        User user;
        try {
            WarehouseDAO warehouseDAO = new WarehouseDAO();
            warehouse = warehouseDAO.findById(resultSet.getLong(TableFields.CHECKOUT_WAREHOUSE_ID)).get();

            UserDAO userDAO = new UserDAO();
            user = userDAO.findById(resultSet.getLong(TableFields.CHECKOUT_USER_ID)).get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return createCheckoutEntity(resultSet, warehouse, user);
    }

    @Override
    public boolean create(CheckoutShift entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_CHECKOUT, Statement.RETURN_GENERATED_KEYS);
            stmt.setTimestamp(1, entity.getDate());
            stmt.setBoolean(2, entity.isClosed());
            stmt.setLong(3, entity.getWarehouse().getId());
            stmt.setLong(4, entity.getUser().getId());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount > 0) {
                try (ResultSet res = stmt.getGeneratedKeys()){
                    if (res.next()) {
                        entity.setId(res.getLong(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean update(CheckoutShift entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_CHECKOUT);
            stmt.setBoolean(1, entity.isClosed());
            stmt.setLong(2, entity.getId());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount == 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean delete(CheckoutShift entity) {
        return false;
    }
}
