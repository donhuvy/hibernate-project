package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@org.hibernate.annotations.DynamicInsert
@org.hibernate.annotations.DynamicUpdate
@Access(AccessType.FIELD)
public class Store {

    @Id
    @GeneratedValue(generator = "STORE_ID_GENERATOR") //pre insert added values on id
    @Column(name = "STORE_ID", unique = true, updatable = false)
    private Long storeId;

    @Embedded @NotNull @Column(nullable = false)
    @AttributeOverrides({@AttributeOverride(name = "locationCity", column = @Column(name = "STORE_LOCATION_CITY")),
    @AttributeOverride(name = "locationCountry", column = @Column(name = "STORE_LOCATION_COUNTRY")),
    @AttributeOverride(name = "locationZipCode.zipCode", column = @Column(name = "STORE_LOCATION_ZIPCODE"))})
    private Location storeLocation;

    @NotNull
    private String storeName;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    //bidirectional, because we want to see where(in WHICH STORE) a particular customer buy things
   // @OneToMany(mappedBy = "customerStore", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST) //ONE Store to MANY Customers
   // @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    //-> java.lang.StackOverflowError: null iarasi, din Store mergem in Customer iar din Customer se merge in Store
    //--> and so on and so on and so on
    //-> making unidirectional, we want to see which clients buys from these Stores, not the other way around
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CUSTOMER_STORE_ID")
    private Collection<Customer> customerCollection = new ArrayList<>();


    //unidirectional
    //fetch EAGER because we want to see right away what categories have our Store
    //...but that means we have a huge graph of objects... Category -> Products, Promotions, Customers...
    //HMMMM
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CATEGORY_STORE_ID")
    @NotNull
    private Set<Category> categorySet = new HashSet<>(); // we don't want duplicate categories in our store

    //bidirectional because we want to see what promotions stores have
    // ONE overall Promotion for ONE Store...
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PROMOTION_STORE_ID")
    private Promotion promotion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) //unidirectional
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "MANY_STORES_TO_MANY_PRODUCTS_IDS",
    joinColumns = @JoinColumn(name = "STORE_ID"),
    inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @NotNull
    @OrderColumn(name = "PRODUCT_POSITION", nullable = false)
    private List<Product> productList = new ArrayList<>();

    protected Store() {}

    //i initialized associations early
    public Store(Location storeLocation, String storeType, String storeName) {
        this.storeLocation = storeLocation;
        this.storeType = Enum.valueOf(StoreType.class, storeType);
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Location getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(Location storeLocation) {
        this.storeLocation = storeLocation;
    }

    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
      //  promotion.setPromotionStore(this);
        this.promotion = promotion;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    //https://vladmihalcea.com/jpa-hibernate-synchronize-bidirectional-entity-associations/
    //but we can instead link together with setters objects -> See Tests
    public void addCustomer(@NotNull Customer customer)
    {
        this.customerCollection.add(customer);
    }

    public void deleteCustomer(@NotNull Customer customer)
    {
        this.customerCollection.remove(customer);
    }


    public void add(@NotNull Category category)
    {
        this.categorySet.add(category);
    }

    public void addProduct(@NotNull Product product)
    {
        this.productList.add(product);
    }
}
