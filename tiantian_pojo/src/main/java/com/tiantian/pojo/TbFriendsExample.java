package com.tiantian.pojo;

import java.util.ArrayList;
import java.util.List;

public class TbFriendsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbFriendsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUseidIsNull() {
            addCriterion("useId is null");
            return (Criteria) this;
        }

        public Criteria andUseidIsNotNull() {
            addCriterion("useId is not null");
            return (Criteria) this;
        }

        public Criteria andUseidEqualTo(Integer value) {
            addCriterion("useId =", value, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidNotEqualTo(Integer value) {
            addCriterion("useId <>", value, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidGreaterThan(Integer value) {
            addCriterion("useId >", value, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidGreaterThanOrEqualTo(Integer value) {
            addCriterion("useId >=", value, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidLessThan(Integer value) {
            addCriterion("useId <", value, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidLessThanOrEqualTo(Integer value) {
            addCriterion("useId <=", value, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidIn(List<Integer> values) {
            addCriterion("useId in", values, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidNotIn(List<Integer> values) {
            addCriterion("useId not in", values, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidBetween(Integer value1, Integer value2) {
            addCriterion("useId between", value1, value2, "useid");
            return (Criteria) this;
        }

        public Criteria andUseidNotBetween(Integer value1, Integer value2) {
            addCriterion("useId not between", value1, value2, "useid");
            return (Criteria) this;
        }

        public Criteria andFriendidIsNull() {
            addCriterion("friendId is null");
            return (Criteria) this;
        }

        public Criteria andFriendidIsNotNull() {
            addCriterion("friendId is not null");
            return (Criteria) this;
        }

        public Criteria andFriendidEqualTo(Integer value) {
            addCriterion("friendId =", value, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidNotEqualTo(Integer value) {
            addCriterion("friendId <>", value, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidGreaterThan(Integer value) {
            addCriterion("friendId >", value, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidGreaterThanOrEqualTo(Integer value) {
            addCriterion("friendId >=", value, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidLessThan(Integer value) {
            addCriterion("friendId <", value, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidLessThanOrEqualTo(Integer value) {
            addCriterion("friendId <=", value, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidIn(List<Integer> values) {
            addCriterion("friendId in", values, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidNotIn(List<Integer> values) {
            addCriterion("friendId not in", values, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidBetween(Integer value1, Integer value2) {
            addCriterion("friendId between", value1, value2, "friendid");
            return (Criteria) this;
        }

        public Criteria andFriendidNotBetween(Integer value1, Integer value2) {
            addCriterion("friendId not between", value1, value2, "friendid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}