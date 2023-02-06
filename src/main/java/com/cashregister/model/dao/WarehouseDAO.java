package com.cashregister.model.dao;

import com.cashregister.model.entity.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseDAO extends AbstractDAO<Long, Warehouse>{

    private static final String SELECT_ALL_WAREHOUSE = "SELECT wh.id, wh.warehouses_name FROM warehouses AS wh;";
    private static final String SELECT_WAREHOUSE_BY_ID = "SELECT wh.id, wh.warehouses_name FROM warehouses AS wh WHERE wh.id = ?;";
    private static final String CREATE_WAREHOUSE = "INSERT INTO warehouses (warehouses_name) values (?);";
    private static final String UPDATE_WAREHOUSE = "UPDATE warehouses SET warehouses_name = ? WHERE id = ?;";


    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> warehousesList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(SELECT_ALL_WAREHOUSE);
            while (resultSet.next()) {
                Warehouse warehouse = createWarehouse(resultSet);
                warehousesList.add(warehouse);
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

        return warehousesList;
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        Warehouse warehouse = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_WAREHOUSE_BY_ID);
            stmt.setInt(1, id.intValue());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                warehouse = createWarehouse(resultSet);
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

        return Optional.ofNullable(warehouse);
    }

    @Override
    public int getRecordsCount() {
        return 0;
    }

    @Override
    public boolean create(Warehouse entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_WAREHOUSE, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getName());

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
    public boolean update(Warehouse entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_WAREHOUSE);
            stmt.setString(1, entity.getName());
            stmt.setLong( 2, entity.getId());

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

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean delete(Warehouse entity) {
        return false;
    }

    private Warehouse createWarehouse(ResultSet resultSet)  throws SQLException{
        long id = resultSet.getLong("id");
        String name = resultSet.getString(TableFields.WAREHOUSES_NAME);

        return new Warehouse(id, name);
    }
}
