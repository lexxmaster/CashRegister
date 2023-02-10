package com.cashregister.model.dao;

import com.cashregister.model.entity.CheckoutShift;
import com.cashregister.model.entity.Report;
import com.cashregister.model.entity.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ReportDAO extends AbstractDAO<Long, Report>{
    public static final String SELECT_ALL_REPORTS = "SELECT report.check_amount, report.check_sum, report.user_id, report.checkout_id FROM z_reports AS report;";
    public static final String CREATE_REPORT = "INSERT INTO z_reports (check_amount, check_sum, user_id, checkout_id) VALUES (?,?,?,?);";
    public static final String SELECT_REPORT_BY_CHECKOUT = "SELECT report.check_amount, report.check_sum, report.user_id, report.checkout_id FROM z_reports AS report WHERE report.checkout_id = ?;";
    public static final String SELECT_REPORT_BY_ID = "SELECT report.check_amount, report.check_sum, report.user_id, report.checkout_id FROM z_reports AS report WHERE report.id = ?;";

    @Override
    public List<Report> findAll() {
        return null;
    }

    @Override
    public Optional<Report> findById(Long id) {
        Report report = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_REPORT_BY_ID);
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                report = createReportEntity(resultSet);
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

        return Optional.ofNullable(report);
    }

    public Optional<Report> findByCheckout(CheckoutShift checkoutShift){
        Report report = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_REPORT_BY_ID);
            stmt.setLong(1, checkoutShift.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                report = createReportEntity(checkoutShift, resultSet);
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

        return Optional.ofNullable(report);
    }
    @Override
    public int getRecordsCount() {
        return 0;
    }

    @Override
    public boolean create(Report entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_REPORT, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, entity.getCheckAmount());
            stmt.setDouble(2, entity.getSum());
            stmt.setLong(3, entity.getUser().getId());
            stmt.setLong(4, entity.getCheckoutShift().getId());

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
    public boolean update(Report entity) {
        return false;
    }

    @Override
    public boolean delete(Report entity) {
        return false;
    }

    private Report createReportEntity(ResultSet resultSet){
        User user;
        CheckoutShift checkoutShift;

        UserDAO userDAO = new UserDAO();
        CheckoutShiftDAO checkoutShiftDAO = new CheckoutShiftDAO();

        try {
            user = userDAO.findById(resultSet.getLong(TableFields.REPORT_USER_ID)).get();
            checkoutShift = checkoutShiftDAO.findById(resultSet.getLong(TableFields.REPORT_CHECKOUT_ID)).get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return createReportEntity(checkoutShift, user, resultSet);
    }

    private Report createReportEntity(CheckoutShift checkoutShift, ResultSet resultSet){
        User user;

        UserDAO userDAO = new UserDAO();

        try {
            user = userDAO.findById(resultSet.getLong(TableFields.REPORT_USER_ID)).get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return createReportEntity(checkoutShift, user, resultSet);
    }
    private Report createReportEntity(CheckoutShift checkoutShift, User user, ResultSet resultSet){
        Report report = new Report(checkoutShift, user);

        try {
            report.setSum(resultSet.getDouble(TableFields.REPORT_SUM));
            report.setCheckAmount(resultSet.getInt(TableFields.REPORT_CHECK_AMOUNT));
            report.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return report;
    }
}
