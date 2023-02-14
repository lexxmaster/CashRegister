package com.cashregister.model.dao;

import com.cashregister.model.entity.Goods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoodsDAO extends AbstractDAO<Long, Goods> {
    private static final String SELECT_ALL_GOODS = "SELECT goods.id, goods.goods_name, goods.weight, goods.scancode, IFNULL(prices.price, 0) AS price FROM goods AS goods LEFT JOIN goods_price AS prices ON goods.id = prices.good_id;";
    private static final String SELECT_ALL_GOODS_PAGE = "SELECT goods.id, goods.goods_name, goods.weight, goods.scancode, IFNULL(prices.price, 0) AS price FROM goods AS goods LEFT JOIN goods_price AS prices ON goods.id = prices.good_id limit ?, ?;";
    private static final String SELECT_GOODS_BY_ID = "SELECT goods.id, goods.goods_name, goods.weight, goods.scancode, IFNULL(prices.price, 0) AS price FROM goods AS goods LEFT JOIN goods_price AS prices ON goods.id = prices.good_id WHERE goods.id = ?;";
    private static final String SELECT_AVAILABLE_AMOUNT = "SELECT amount FROM cash_register.goods_amount WHERE good_id = ? AND warehouse_id = ?;";
    private static final String SELECT_GOODS_BY_NAME = "SELECT goods.id, goods.goods_name, goods.weight, goods.scancode, IFNULL(prices.price, 0) AS price FROM goods AS goods LEFT JOIN goods_price AS prices ON goods.id = prices.good_id WHERE goods.goods_name = ?;";
    private static final String SELECT_GOODS_BY_SCANCODE = "SELECT goods.id, goods.goods_name, goods.weight, goods.scancode, IFNULL(prices.price, 0) AS price FROM goods AS goods LEFT JOIN goods_price AS prices ON goods.id = prices.good_id WHERE goods.scancode = ?;";
    private static final String GET_RECORDS_COUNT = "SELECT count(goods.id) AS goods_count FROM goods AS goods;";
    private static final String CREATE_GOODS = "INSERT INTO goods (goods_name, weight, scancode) values (?,?,?);";
    private static final String CREATE_PRICE = "INSERT INTO goods_price (good_id, price) values (?,?);";
    private static final String UPDATE_GOODS = "UPDATE goods SET goods_name = ?, weight = ?, scancode = ? WHERE id = ?;";
    private static final String UPDATE_PRICE = "UPDATE goods_price SET price = ? WHERE good_id = ?;";
    public static final String UPDATE_AMOUNT = "INSERT INTO goods_amount (good_id, warehouse_id, amount) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE amount = ?;";

    @Override
    public List<Goods> findAll() {
        List<Goods> goodsList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(SELECT_ALL_GOODS);
            while (resultSet.next()) {
                Goods goods = createGoods(resultSet);
                goodsList.add(goods);
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

        return goodsList;
    }

    public List<Goods> findAllByPage(int offset, int recordsPerPage) {
        List<Goods> goodsList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_ALL_GOODS_PAGE);
            stmt.setInt(1, offset);
            stmt.setInt(2, recordsPerPage);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Goods goods = createGoods(resultSet);
                goodsList.add(goods);
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

        return goodsList;
    }
    @Override
    public Optional<Goods> findById(Long id) {
        Goods goods = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_GOODS_BY_ID);
            stmt.setInt(1, id.intValue());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                goods = createGoods(resultSet);
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

        return Optional.ofNullable(goods);
    }

    @Override
    public int getRecordsCount() {
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = null;
        int count = 1;
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(GET_RECORDS_COUNT);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
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

        return count;
    }

    public Optional<Goods> findByName(String name) {
        Goods goods = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_GOODS_BY_NAME);
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                goods = createGoods(resultSet);
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

        return Optional.ofNullable(goods);
    }

    public Optional<Goods> findByScancode(String scancode) {
        Goods goods = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_GOODS_BY_SCANCODE);
            stmt.setString(1, scancode);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                goods = createGoods(resultSet);
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

        return Optional.ofNullable(goods);
    }

    @Override
    public boolean create(Goods entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_GOODS, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getName());
            stmt.setBoolean(2, entity.isWeight());
            stmt.setString(3, entity.getScancode());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount > 0) {
                try (ResultSet res = stmt.getGeneratedKeys()){
                    if (res.next()) {
                        entity.setId(res.getLong(1));
                        createPrice(entity, con);
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
    public void createPrice(Goods entity, Connection con) {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_PRICE);
            stmt.setLong(1, entity.getId());
            stmt.setDouble(2, entity.getPrice());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount == 0) {
                throw new RuntimeException();
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
    }
    @Override
    public boolean update(Goods entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_GOODS);
            stmt.setString(1, entity.getName());
            stmt.setBoolean(2, entity.isWeight());
            stmt.setString(3, entity.getScancode());
            stmt.setLong( 4, entity.getId());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount > 0) {
                updatePrice(entity, con);
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
    public boolean delete(Goods entity) {
        return false;
    }

    public void updatePrice(Goods entity, Connection con) {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_PRICE);
            stmt.setDouble(1, entity.getPrice());
            stmt.setLong(2, entity.getId());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount == 0) {
                throw new RuntimeException();
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
    }

    private Goods createGoods(ResultSet resultSet)  throws SQLException{
        long id = resultSet.getLong("id");
        String name = resultSet.getString(TableFields.GOODS_NAME);
        String scanCode = resultSet.getString(TableFields.GOODS_SCANCODE);
        boolean weight = resultSet.getBoolean(TableFields.GOODS_WEIGHT);
        double price = resultSet.getDouble(TableFields.GOODS_PRICE_PRICE);

        Goods goods = new Goods(id, name);
        goods.setScancode(scanCode);
        goods.setWeight(weight);
        goods.setPrice(price);

        return goods;
    }

    public double getAvailableAmount(long warehouse_id, long goods_id){
        double amount = 0.0;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_AVAILABLE_AMOUNT);
            stmt.setLong(1, goods_id);
            stmt.setLong(2, warehouse_id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getDouble(TableFields.GOODS_AMOUNT_AMOUNT);
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

        return amount;
    }

    public boolean setAvailableAmount(long warehouse_id, long goods_id, double amount){
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_AMOUNT);
            stmt.setLong(1, goods_id);
            stmt.setLong(2, warehouse_id);
            stmt.setDouble(3, amount);
            stmt.setDouble(4, amount);

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount == 0) {
                throw new RuntimeException();
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


}
