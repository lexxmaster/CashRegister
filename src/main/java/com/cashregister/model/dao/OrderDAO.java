package com.cashregister.model.dao;

import com.cashregister.model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<Long, Order>{
    private static final String SELECT_ALL_ORDERS = "SELECT orders.id, orders.checkout_id, orders.warehouses_id, orders.order_date, orders.closed, orders.user_id FROM orders AS orders;";
    private static final String SELECT_ORDERS_BY_CHECKOUT = "SELECT orders.id, orders.checkout_id, orders.warehouses_id, orders.order_date, orders.closed, orders.user_id FROM orders AS orders WHERE orders.checkout_id = ?;";
    private static final String SELECT_ORDERS_BY_CHECKOUT_PAGE = "SELECT orders.id, orders.checkout_id, orders.warehouses_id, orders.order_date, orders.closed, orders.user_id FROM orders AS orders WHERE orders.checkout_id = ? limit ?, ?;";
    private static final String SELECT_ORDER_GOODS = "SELECT goods.good_id, goods.amount, goods.price, goods.total FROM order_goods AS goods WHERE goods.order_id = ?;";
    private static final String SELECT_ORDER_BY_ID = "SELECT orders.id, orders.checkout_id, orders.warehouses_id, orders.order_date, orders.closed, orders.user_id FROM orders AS orders WHERE orders.id = ?;";
    private static final String GET_RECORDS_COUNT = "SELECT count(orders.id) AS orders_count FROM orders AS orders;";
    private static final String CREATE_ORDER = "INSERT INTO orders (warehouses_id, checkout_id, order_date, closed, user_id) values (?,?,?,?,?);";
    private static final String UPDATE_ORDER = "UPDATE orders SET warehouses_id = ?, order_date = ?, closed = ? WHERE id = ?;";
    private static final String CREATE_ORDER_GOODS = "INSERT INTO order_goods (order_id, good_id, amount, price, total) values (?,?,?,?,?);";
    private static final String UPDATE_ORDER_GOODS = "UPDATE order_goods SET amount = ?, price = ?, total = ? WHERE order_id = ? AND good_id = ?;";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?;";
    private static final String DELETE_ORDER_GOODS = "DELETE FROM order_goods WHERE order_id = ? AND good_id = ?;";
    private static final String CLEAR_ORDER_GOODS = "DELETE FROM order_goods WHERE order_id = ?;";

    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(SELECT_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = createOrderEntity(resultSet);
                findOrderGoods(order, con);
                order.updateTotal();
                orderList.add(order);
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

        return orderList;
    }

    public List<Order> findByCheckout(CheckoutShift checkoutShift) {
        List<Order> orderList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_ORDERS_BY_CHECKOUT);
            stmt.setLong(1, checkoutShift.getId());

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Order order = createOrderEntity(resultSet);
                findOrderGoods(order, con);
                order.updateTotal();
                orderList.add(order);
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

        return orderList;
    }

    public List<Order> findByCheckoutByPage(CheckoutShift checkoutShift, int offset, int recordsPerPage) {
        List<Order> orderList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_ORDERS_BY_CHECKOUT_PAGE);
            stmt.setLong(1, checkoutShift.getId());
            stmt.setInt(2, offset);
            stmt.setInt(3, recordsPerPage);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Order order = createOrderEntity(resultSet);
                findOrderGoods(order, con);
                order.updateTotal();
                orderList.add(order);
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

        return orderList;
    }

    private void findOrderGoods(Order order, Connection con) {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_ORDER_GOODS);
            stmt.setLong(1, order.getId());
            ResultSet resultSet = stmt.executeQuery();

            GoodsDAO goodsDAO = new GoodsDAO();
            while (resultSet.next()) {
                Goods goods = goodsDAO.findById(resultSet.getLong(TableFields.GOODS_AMOUNT_GOOD_ID)).get();
                double amount = resultSet.getDouble(TableFields.ORDER_GOODS_AMOUNT);
                double price = resultSet.getDouble(TableFields.ORDER_GOODS_PRICE);
                double total = resultSet.getDouble(TableFields.ORDER_GOODS_TOTAL);
                order.getGoodsList().put(goods, new GoodsAmount(amount, price, total));
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
    public Optional<Order> findById(Long id) {
        Order order = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_ORDER_BY_ID);
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                order = createOrderEntity(resultSet);
                findOrderGoods(order, con);
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

        return Optional.ofNullable(order);
    }

    @Override
    public int getRecordsCount() {
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = null;
        int count = 0;
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

    @Override
    public boolean create(Order entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, entity.getWarehouse().getId());
            stmt.setLong(2, entity.getCheckoutShift().getId());
            stmt.setTimestamp(3, entity.getDate());
            stmt.setBoolean(4, entity.isClosed());
            stmt.setLong(5, entity.getUser().getId());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount > 0) {
                try (ResultSet res = stmt.getGeneratedKeys()){
                    if (res.next()) {
                        entity.setId(res.getLong(1));
                    }
                }
            }
//            ResultSet resultSet = stmt.executeQuery();
//            if (resultSet != null && resultSet.next()) {
//                 long id = resultSet.getLong("id");
//                 entity.setId(id);
//            }

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
    public boolean update(Order entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_ORDER);
            stmt.setLong(1, entity.getWarehouse().getId());
            stmt.setTimestamp(2, entity.getDate());
            stmt.setBoolean(3, entity.isClosed());
            stmt.setLong(4, entity.getId());

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
    public boolean delete(Order entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(DELETE_ORDER);
            stmt.setLong(1, entity.getId());

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

    private Order createOrderEntity(ResultSet resultSet)  throws SQLException{
        long id = resultSet.getLong("id");
        Timestamp date = resultSet.getTimestamp(TableFields.ORDER_ORDER_DATE);
        long warehouseId = resultSet.getLong(TableFields.ORDER_WAREHOUSES_ID);
        long checkoutId = resultSet.getLong(TableFields.ORDER_CHECKOUT_ID);
        boolean closed = resultSet.getBoolean(TableFields.ORDER_CLOSED);
        long user_id = resultSet.getLong(TableFields.ORDER_USER_ID);

        Warehouse warehouse = (new WarehouseDAO().findById(warehouseId)).get(); //replace with cash?
        CheckoutShift checkoutShift = (new CheckoutShiftDAO().findById(checkoutId)).get();
        User user = (new UserDAO().findById(user_id)).get();

        Order order = new Order(id, warehouse, date);
        order.setClosed(closed);
        order.setCheckoutShift(checkoutShift);
        order.setUser(user);

        return order;
    }

    public boolean createOrderGoods(Order order, Goods goods) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            GoodsAmount goodsAmount = order.getGoodsList().get(goods);
            stmt = con.prepareStatement(CREATE_ORDER_GOODS);
            stmt.setLong(1, order.getId());
            stmt.setLong(2, goods.getId());
            stmt.setDouble(3, goodsAmount.getAmount());
            stmt.setDouble(4, goodsAmount.getPrice());
            stmt.setDouble(5, goodsAmount.getTotal());

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

    public boolean updateOrderGoods(Order order, Goods goods) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            GoodsAmount goodsAmount = order.getGoodsList().get(goods);
            stmt = con.prepareStatement(UPDATE_ORDER_GOODS);
            stmt.setDouble(1, goodsAmount.getAmount());
            stmt.setDouble(2, goodsAmount.getPrice());
            stmt.setDouble(3, goodsAmount.getTotal());
            stmt.setLong(4, order.getId());
            stmt.setLong(5, goods.getId());

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

    public boolean deleteOrderGoods(Order order, Goods goods) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(DELETE_ORDER_GOODS);
            stmt.setLong(1, order.getId());
            stmt.setLong(2, goods.getId());

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

    public boolean clearOrderGoods(Order order) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CLEAR_ORDER_GOODS);
            stmt.setLong(1, order.getId());

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

}
