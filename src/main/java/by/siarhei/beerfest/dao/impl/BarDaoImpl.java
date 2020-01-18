package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.connection.ProxyConnection;
import by.siarhei.beerfest.dao.BarDao;
import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.factory.BarFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarDaoImpl implements BarDao {
    private static final String COINCIDENCES_RESULT_INDEX = "coincidences";
    private static final String INSERT_BAR_SQL = "INSERT INTO bar (account_id,name,description,food_id,beer_id ,places) VALUES (?,?,?,?,?,?)";
    private static final String INSERT_BEER_SQL = "INSERT INTO beer (name) VALUES (?)";
    private static final String INSERT_FOOD_SQL = "INSERT INTO food (name) VALUES (?)";
    private static final String SELECT_BEER_ID_BY_NAME = "SELECT id FROM beer WHERE name=?";
    private static final String SELECT_FOOD_ID_BY_NAME = "SELECT id FROM food WHERE name=?";
    private static final String SELECT_ALL_FOOD ="SELECT food.id,food.name FROM food ";
    private static final String SELECT_ALL_BEER = "SELECT beer.id,beer.name FROM beer ";
    private static final String CHECK_BAR_BY_USER_LOGIN = String.format(
            "SELECT count(*) as %s " +
                    "FROM bar " +
                    "INNER JOIN account " +
                    "ON bar.account_id = account.id " +
                    "WHERE account.login= ? ", COINCIDENCES_RESULT_INDEX);
    private static final String SELECT_ALL_BAR_SQL =
            "SELECT bar.id,bar.account_id,bar.name,bar.description,bar.food_id,food.name,bar.beer_id,beer.name,bar.places " +
                    "FROM bar " +
                    "INNER JOIN beer " +
                    "ON bar.beer_id = beer.id " +
                    "INNER JOIN food " +
                    "ON bar.food_id = food.id ";

    @Override
    public List<Bar> findAll() throws FeedUpdateException {
        List<Bar> list = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_BAR_SQL);
            BarFactory factory = BarFactory.getInstance();
            while (resultSet.next()) {
                int index = 0;
                long barId = resultSet.getLong(++index);
                long accountId = resultSet.getLong(++index);
                String name = resultSet.getString(++index);
                String description = resultSet.getString(++index);
                long foodId = resultSet.getLong(++index);
                String foodName = resultSet.getString(++index);
                long beerId = resultSet.getLong(++index);
                String beerName = resultSet.getString(++index);
                int places = resultSet.getInt(++index);
                list.add(factory.create(barId, accountId, name, description, foodId, foodName, beerId, beerName, places));
            }

        } catch (SQLException e) {
            logger.error(String.format("Bar list cant be updated throws exception: %s", e));
            throw new FeedUpdateException(e);
        } finally {
            close(connection);
            close(statement);
            close(resultSet);
        }
        return list;
    }

    @Override
    public Bar findEntity(Long id) {
        return null;
    }

    @Override
    public boolean delete(Bar entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Bar update(Bar entity) {
        return null;
    }

    @Override
    public int findBeerIdByName(String name) {
        int id = 0;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_BEER_ID_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot get beer id by name: %s throws exception: %s", name, e));
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return id;
    }

    @Override
    public int findFoodIdByName(String name) {
        int id = 0;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_FOOD_ID_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot get food id by name: %s throws exception: %s", name, e));
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return id;
    }

    @Override
    public boolean create(Bar bar) {
        boolean flag = false;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_BAR_SQL);
            int index = 1;
            statement.setLong(index++, bar.getAccountId());
            statement.setString(index++, bar.getName());
            statement.setString(index++, bar.getDescription());
            statement.setLong(index++, bar.getFoodId());
            statement.setLong(index++, bar.getBeerId());
            statement.setLong(index, bar.getPlaces());
            statement.execute();
            logger.info(String.format("Created bar: %s", bar));
            flag = true;
        } catch (SQLException e) {
            logger.error(String.format("Cannot insert new user throws exception: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
        return flag;
    }

    @Override
    public boolean isUserSubmittedBar(String login) {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CHECK_BAR_BY_USER_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = resultSet.getInt(COINCIDENCES_RESULT_INDEX) != 0;
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot check bar submission exists throws exception: %s", e));
            flag = true;
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return flag;
    }

    @Override
    public Map<Long, String> findAllFoodType() throws FeedUpdateException {
        Map<Long, String> foodList = new HashMap<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_FOOD);
            while (resultSet.next()) {
                int index = 1;
                long foodId = resultSet.getLong(index++);
                String foodName = resultSet.getString(index);
                foodList.put(foodId, foodName);
            }

        } catch (SQLException e) {
            logger.error(String.format("Cant get food list throws exception: %s", e));
            throw new FeedUpdateException(e);
        } finally {
            close(connection);
            close(statement);
            close(resultSet);
        }
        return foodList;
    }

    @Override
    public Map<Long, String> findnAllBeerType() throws FeedUpdateException {
        Map<Long, String> beerList = new HashMap<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_BEER);
            while (resultSet.next()) {
                int index = 1;
                long beerId = resultSet.getLong(index++);
                String beerName = resultSet.getString(index);
                beerList.put(beerId, beerName);
            }

        } catch (SQLException e) {
            logger.error(String.format("Cant get beer list throws exception: %s", e));
            throw new FeedUpdateException(e);
        } finally {
            close(connection);
            close(statement);
            close(resultSet);
        }
        return beerList;
    }

    @Override
    public boolean submitBeer(String beerName) {
        boolean flag = false;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_BEER_SQL);
            int index = 1;
            statement.setString(index, beerName);
            statement.execute();
            logger.info(String.format("Added beer: %s", beerName));
            flag = true;
        } catch (SQLException e) {
            logger.error(String.format("Cannot insert new beer throws exception: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
        return flag;
    }

    @Override
    public boolean submitFood(String beerName) {
        boolean flag = false;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_FOOD_SQL);
            int index = 1;
            statement.setString(index, beerName);
            statement.execute();
            logger.info(String.format("Added food: %s", beerName));
            flag = true;
        } catch (SQLException e) {
            logger.error(String.format("Cannot insert new food throws exception: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
        return flag;
    }
}