package cn.com.search.model;

import javax.persistence.*;

@Table(name = "bbg_dtp_mom_item")
public class GoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品ID  PK 取交易层级
     */
    private String item;

    /**
     * 父商品
     */
    @Column(name = "item_parent")
    private String itemParent;

    /**
     * 包装商品标识 Y-包装商品，N-非包装商品
     */
    @Column(name = "pack_ind")
    private String packInd;

    /**
     * 商品层级（1-第一层商品 2-第二层商品 3-第三层商品）
     */
    @Column(name = "item_level")
    private Integer itemLevel;

    /**
     * 交易层级（1-第一层商品 2-第二层商品 3-第三层商品）
     */
    @Column(name = "tran_level")
    private Integer tranLevel;

    /**
     * 部门
     */
    @Column(name = "item_group")
    private Integer itemGroup;

    /**
     * 大类
     */
    @Column(name = "item_dept")
    private Integer itemDept;

    /**
     * 中类
     */
    @Column(name = "item_class")
    private Integer itemClass;

    /**
     * 小类
     */
    @Column(name = "item_subclass")
    private Integer itemSubclass;

    /**
     * 状态  A-Approved
     */
    @Column(name = "item_status")
    private String itemStatus;

    /**
     * 商品描述
     */
    @Column(name = "item_desc")
    private String itemDesc;

    /**
     * 品牌(中文描述)
     */
    private String brand;

    /**
     * 产地（中文描述）
     */
    @Column(name = "place_of_production")
    private String placeOfProduction;

    /**
     * 标准度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     */
    @Column(name = "standard_uom")
    private String standardUom;

    /**
     * 包装数量
     */
    @Column(name = "package_size")
    private Double packageSize;

    /**
     * 包装度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     */
    @Column(name = "package_uom")
    private String packageUom;

    /**
     * 建议零售价
     */
    @Column(name = "mfg_rec_retail")
    private Double mfgRecRetail;

    /**
     * 标重标识（Y-称重商品，N-非称重商品）
     */
    @Column(name = "catch_weight_ind")
    private String catchWeightInd;

    /**
     * 默认损耗比例
     */
    @Column(name = "default_waste_pct")
    private Double defaultWastePct;

    /**
     * 商品短描述
     */
    @Column(name = "short_desc")
    private String shortDesc;

    /**
     * 库存标识（Y-可库存商品，N-非可库存商品）
     */
    @Column(name = "inventory_ind")
    private String inventoryInd;

    /**
     * 是否为商品（Y表示商品，N表示非商品）
     */
    @Column(name = "merchandise_ind")
    private String merchandiseInd;

    /**
     * 是否生鲜产品（Y-生鲜商品，N-非生鲜商品）
     */
    @Column(name = "fresh_item_ind")
    private String freshItemInd;

    /**
     * 是否为柜组码（Y表示柜组，N表示单品。柜组商品在前台POS机上允许改价销售）
     */
    @Column(name = "joint_item_ind")
    private String jointItemInd;

    /**
     * 进项税率（只取已生效的进项税）
     */
    @Column(name = "vat_in_rate")
    private Double vatInRate;

    /**
     * 销项税率（只取已生效的销项税）
     */
    @Column(name = "vat_out_rate")
    private Double vatOutRate;

    /**
     * 创建时间（时间格式YYYYMMDDHH24MISS）
     */
    @Column(name = "create_datetime")
    private String createDatetime;

    /**
     * 最后更新人
     */
    @Column(name = "last_update_id")
    private String lastUpdateId;

    /**
     * 最后更新时间（时间格式YYYYMMDDHH24MISS）
     */
    @Column(name = "last_update_datetime")
    private String lastUpdateDatetime;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 编码类型（CODE_TYPE=’ UPCT’,取条码类型）
     */
    @Column(name = "item_number_type")
    private String itemNumberType;

    /**
     * 是否为主条码（对于一品多条码的情况，需标记此条码是否为主条码。如果为主条码，则写入主档表，并作为标签打印条码）
     */
    @Column(name = "primary_ref_item_ind")
    private String primaryRefItemInd;

    /**
     * 修改类型（ADD-新增 MOD-修改 DEL-删除）
     */
    @Column(name = "mod_type")
    private String modType;

    /**
     * 发布时间（YYYYMMDDHH24MISS）
     */
    @Column(name = "publish_time")
    private String publishTime;

    /**
     * 可销标识
     */
    @Column(name = "sellable_ind")
    private String sellableInd;

    /**
     * 差异商品描述
     */
    @Column(name = "diff_desc")
    private String diffDesc;

    /**
     * 附加属性3~10（冗余字段）
     */
    @Column(name = "add_atrrib3_10")
    private String addAtrrib310;

    private String disable;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品ID  PK 取交易层级
     *
     * @return item - 商品ID  PK 取交易层级
     */
    public String getItem() {
        return item;
    }

    /**
     * 设置商品ID  PK 取交易层级
     *
     * @param item 商品ID  PK 取交易层级
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 获取父商品
     *
     * @return item_parent - 父商品
     */
    public String getItemParent() {
        return itemParent;
    }

    /**
     * 设置父商品
     *
     * @param itemParent 父商品
     */
    public void setItemParent(String itemParent) {
        this.itemParent = itemParent;
    }

    /**
     * 获取包装商品标识 Y-包装商品，N-非包装商品
     *
     * @return pack_ind - 包装商品标识 Y-包装商品，N-非包装商品
     */
    public String getPackInd() {
        return packInd;
    }

    /**
     * 设置包装商品标识 Y-包装商品，N-非包装商品
     *
     * @param packInd 包装商品标识 Y-包装商品，N-非包装商品
     */
    public void setPackInd(String packInd) {
        this.packInd = packInd;
    }

    /**
     * 获取商品层级（1-第一层商品 2-第二层商品 3-第三层商品）
     *
     * @return item_level - 商品层级（1-第一层商品 2-第二层商品 3-第三层商品）
     */
    public Integer getItemLevel() {
        return itemLevel;
    }

    /**
     * 设置商品层级（1-第一层商品 2-第二层商品 3-第三层商品）
     *
     * @param itemLevel 商品层级（1-第一层商品 2-第二层商品 3-第三层商品）
     */
    public void setItemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
    }

    /**
     * 获取交易层级（1-第一层商品 2-第二层商品 3-第三层商品）
     *
     * @return tran_level - 交易层级（1-第一层商品 2-第二层商品 3-第三层商品）
     */
    public Integer getTranLevel() {
        return tranLevel;
    }

    /**
     * 设置交易层级（1-第一层商品 2-第二层商品 3-第三层商品）
     *
     * @param tranLevel 交易层级（1-第一层商品 2-第二层商品 3-第三层商品）
     */
    public void setTranLevel(Integer tranLevel) {
        this.tranLevel = tranLevel;
    }

    /**
     * 获取部门
     *
     * @return item_group - 部门
     */
    public Integer getItemGroup() {
        return itemGroup;
    }

    /**
     * 设置部门
     *
     * @param itemGroup 部门
     */
    public void setItemGroup(Integer itemGroup) {
        this.itemGroup = itemGroup;
    }

    /**
     * 获取大类
     *
     * @return item_dept - 大类
     */
    public Integer getItemDept() {
        return itemDept;
    }

    /**
     * 设置大类
     *
     * @param itemDept 大类
     */
    public void setItemDept(Integer itemDept) {
        this.itemDept = itemDept;
    }

    /**
     * 获取中类
     *
     * @return item_class - 中类
     */
    public Integer getItemClass() {
        return itemClass;
    }

    /**
     * 设置中类
     *
     * @param itemClass 中类
     */
    public void setItemClass(Integer itemClass) {
        this.itemClass = itemClass;
    }

    /**
     * 获取小类
     *
     * @return item_subclass - 小类
     */
    public Integer getItemSubclass() {
        return itemSubclass;
    }

    /**
     * 设置小类
     *
     * @param itemSubclass 小类
     */
    public void setItemSubclass(Integer itemSubclass) {
        this.itemSubclass = itemSubclass;
    }

    /**
     * 获取状态  A-Approved
     *
     * @return item_status - 状态  A-Approved
     */
    public String getItemStatus() {
        return itemStatus;
    }

    /**
     * 设置状态  A-Approved
     *
     * @param itemStatus 状态  A-Approved
     */
    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     * 获取商品描述
     *
     * @return item_desc - 商品描述
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * 设置商品描述
     *
     * @param itemDesc 商品描述
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    /**
     * 获取品牌(中文描述)
     *
     * @return brand - 品牌(中文描述)
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌(中文描述)
     *
     * @param brand 品牌(中文描述)
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取产地（中文描述）
     *
     * @return place_of_production - 产地（中文描述）
     */
    public String getPlaceOfProduction() {
        return placeOfProduction;
    }

    /**
     * 设置产地（中文描述）
     *
     * @param placeOfProduction 产地（中文描述）
     */
    public void setPlaceOfProduction(String placeOfProduction) {
        this.placeOfProduction = placeOfProduction;
    }

    /**
     * 获取标准度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     *
     * @return standard_uom - 标准度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     */
    public String getStandardUom() {
        return standardUom;
    }

    /**
     * 设置标准度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     *
     * @param standardUom 标准度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     */
    public void setStandardUom(String standardUom) {
        this.standardUom = standardUom;
    }

    /**
     * 获取包装数量
     *
     * @return package_size - 包装数量
     */
    public Double getPackageSize() {
        return packageSize;
    }

    /**
     * 设置包装数量
     *
     * @param packageSize 包装数量
     */
    public void setPackageSize(Double packageSize) {
        this.packageSize = packageSize;
    }

    /**
     * 获取包装度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     *
     * @return package_uom - 包装度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     */
    public String getPackageUom() {
        return packageUom;
    }

    /**
     * 设置包装度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     *
     * @param packageUom 包装度量单位（为ID，接口接入时需根据UOM表进行转换为描述）
     */
    public void setPackageUom(String packageUom) {
        this.packageUom = packageUom;
    }

    /**
     * 获取建议零售价
     *
     * @return mfg_rec_retail - 建议零售价
     */
    public Double getMfgRecRetail() {
        return mfgRecRetail;
    }

    /**
     * 设置建议零售价
     *
     * @param mfgRecRetail 建议零售价
     */
    public void setMfgRecRetail(Double mfgRecRetail) {
        this.mfgRecRetail = mfgRecRetail;
    }

    /**
     * 获取标重标识（Y-称重商品，N-非称重商品）
     *
     * @return catch_weight_ind - 标重标识（Y-称重商品，N-非称重商品）
     */
    public String getCatchWeightInd() {
        return catchWeightInd;
    }

    /**
     * 设置标重标识（Y-称重商品，N-非称重商品）
     *
     * @param catchWeightInd 标重标识（Y-称重商品，N-非称重商品）
     */
    public void setCatchWeightInd(String catchWeightInd) {
        this.catchWeightInd = catchWeightInd;
    }

    /**
     * 获取默认损耗比例
     *
     * @return default_waste_pct - 默认损耗比例
     */
    public Double getDefaultWastePct() {
        return defaultWastePct;
    }

    /**
     * 设置默认损耗比例
     *
     * @param defaultWastePct 默认损耗比例
     */
    public void setDefaultWastePct(Double defaultWastePct) {
        this.defaultWastePct = defaultWastePct;
    }

    /**
     * 获取商品短描述
     *
     * @return short_desc - 商品短描述
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * 设置商品短描述
     *
     * @param shortDesc 商品短描述
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    /**
     * 获取库存标识（Y-可库存商品，N-非可库存商品）
     *
     * @return inventory_ind - 库存标识（Y-可库存商品，N-非可库存商品）
     */
    public String getInventoryInd() {
        return inventoryInd;
    }

    /**
     * 设置库存标识（Y-可库存商品，N-非可库存商品）
     *
     * @param inventoryInd 库存标识（Y-可库存商品，N-非可库存商品）
     */
    public void setInventoryInd(String inventoryInd) {
        this.inventoryInd = inventoryInd;
    }

    /**
     * 获取是否为商品（Y表示商品，N表示非商品）
     *
     * @return merchandise_ind - 是否为商品（Y表示商品，N表示非商品）
     */
    public String getMerchandiseInd() {
        return merchandiseInd;
    }

    /**
     * 设置是否为商品（Y表示商品，N表示非商品）
     *
     * @param merchandiseInd 是否为商品（Y表示商品，N表示非商品）
     */
    public void setMerchandiseInd(String merchandiseInd) {
        this.merchandiseInd = merchandiseInd;
    }

    /**
     * 获取是否生鲜产品（Y-生鲜商品，N-非生鲜商品）
     *
     * @return fresh_item_ind - 是否生鲜产品（Y-生鲜商品，N-非生鲜商品）
     */
    public String getFreshItemInd() {
        return freshItemInd;
    }

    /**
     * 设置是否生鲜产品（Y-生鲜商品，N-非生鲜商品）
     *
     * @param freshItemInd 是否生鲜产品（Y-生鲜商品，N-非生鲜商品）
     */
    public void setFreshItemInd(String freshItemInd) {
        this.freshItemInd = freshItemInd;
    }

    /**
     * 获取是否为柜组码（Y表示柜组，N表示单品。柜组商品在前台POS机上允许改价销售）
     *
     * @return joint_item_ind - 是否为柜组码（Y表示柜组，N表示单品。柜组商品在前台POS机上允许改价销售）
     */
    public String getJointItemInd() {
        return jointItemInd;
    }

    /**
     * 设置是否为柜组码（Y表示柜组，N表示单品。柜组商品在前台POS机上允许改价销售）
     *
     * @param jointItemInd 是否为柜组码（Y表示柜组，N表示单品。柜组商品在前台POS机上允许改价销售）
     */
    public void setJointItemInd(String jointItemInd) {
        this.jointItemInd = jointItemInd;
    }

    /**
     * 获取进项税率（只取已生效的进项税）
     *
     * @return vat_in_rate - 进项税率（只取已生效的进项税）
     */
    public Double getVatInRate() {
        return vatInRate;
    }

    /**
     * 设置进项税率（只取已生效的进项税）
     *
     * @param vatInRate 进项税率（只取已生效的进项税）
     */
    public void setVatInRate(Double vatInRate) {
        this.vatInRate = vatInRate;
    }

    /**
     * 获取销项税率（只取已生效的销项税）
     *
     * @return vat_out_rate - 销项税率（只取已生效的销项税）
     */
    public Double getVatOutRate() {
        return vatOutRate;
    }

    /**
     * 设置销项税率（只取已生效的销项税）
     *
     * @param vatOutRate 销项税率（只取已生效的销项税）
     */
    public void setVatOutRate(Double vatOutRate) {
        this.vatOutRate = vatOutRate;
    }

    /**
     * 获取创建时间（时间格式YYYYMMDDHH24MISS）
     *
     * @return create_datetime - 创建时间（时间格式YYYYMMDDHH24MISS）
     */
    public String getCreateDatetime() {
        return createDatetime;
    }

    /**
     * 设置创建时间（时间格式YYYYMMDDHH24MISS）
     *
     * @param createDatetime 创建时间（时间格式YYYYMMDDHH24MISS）
     */
    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * 获取最后更新人
     *
     * @return last_update_id - 最后更新人
     */
    public String getLastUpdateId() {
        return lastUpdateId;
    }

    /**
     * 设置最后更新人
     *
     * @param lastUpdateId 最后更新人
     */
    public void setLastUpdateId(String lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    /**
     * 获取最后更新时间（时间格式YYYYMMDDHH24MISS）
     *
     * @return last_update_datetime - 最后更新时间（时间格式YYYYMMDDHH24MISS）
     */
    public String getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    /**
     * 设置最后更新时间（时间格式YYYYMMDDHH24MISS）
     *
     * @param lastUpdateDatetime 最后更新时间（时间格式YYYYMMDDHH24MISS）
     */
    public void setLastUpdateDatetime(String lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    /**
     * 获取商品条码
     *
     * @return barcode - 商品条码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置商品条码
     *
     * @param barcode 商品条码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 获取编码类型（CODE_TYPE=’ UPCT’,取条码类型）
     *
     * @return item_number_type - 编码类型（CODE_TYPE=’ UPCT’,取条码类型）
     */
    public String getItemNumberType() {
        return itemNumberType;
    }

    /**
     * 设置编码类型（CODE_TYPE=’ UPCT’,取条码类型）
     *
     * @param itemNumberType 编码类型（CODE_TYPE=’ UPCT’,取条码类型）
     */
    public void setItemNumberType(String itemNumberType) {
        this.itemNumberType = itemNumberType;
    }

    /**
     * 获取是否为主条码（对于一品多条码的情况，需标记此条码是否为主条码。如果为主条码，则写入主档表，并作为标签打印条码）
     *
     * @return primary_ref_item_ind - 是否为主条码（对于一品多条码的情况，需标记此条码是否为主条码。如果为主条码，则写入主档表，并作为标签打印条码）
     */
    public String getPrimaryRefItemInd() {
        return primaryRefItemInd;
    }

    /**
     * 设置是否为主条码（对于一品多条码的情况，需标记此条码是否为主条码。如果为主条码，则写入主档表，并作为标签打印条码）
     *
     * @param primaryRefItemInd 是否为主条码（对于一品多条码的情况，需标记此条码是否为主条码。如果为主条码，则写入主档表，并作为标签打印条码）
     */
    public void setPrimaryRefItemInd(String primaryRefItemInd) {
        this.primaryRefItemInd = primaryRefItemInd;
    }

    /**
     * 获取修改类型（ADD-新增 MOD-修改 DEL-删除）
     *
     * @return mod_type - 修改类型（ADD-新增 MOD-修改 DEL-删除）
     */
    public String getModType() {
        return modType;
    }

    /**
     * 设置修改类型（ADD-新增 MOD-修改 DEL-删除）
     *
     * @param modType 修改类型（ADD-新增 MOD-修改 DEL-删除）
     */
    public void setModType(String modType) {
        this.modType = modType;
    }

    /**
     * 获取发布时间（YYYYMMDDHH24MISS）
     *
     * @return publish_time - 发布时间（YYYYMMDDHH24MISS）
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间（YYYYMMDDHH24MISS）
     *
     * @param publishTime 发布时间（YYYYMMDDHH24MISS）
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取可销标识
     *
     * @return sellable_ind - 可销标识
     */
    public String getSellableInd() {
        return sellableInd;
    }

    /**
     * 设置可销标识
     *
     * @param sellableInd 可销标识
     */
    public void setSellableInd(String sellableInd) {
        this.sellableInd = sellableInd;
    }

    /**
     * 获取差异商品描述
     *
     * @return diff_desc - 差异商品描述
     */
    public String getDiffDesc() {
        return diffDesc;
    }

    /**
     * 设置差异商品描述
     *
     * @param diffDesc 差异商品描述
     */
    public void setDiffDesc(String diffDesc) {
        this.diffDesc = diffDesc;
    }

    /**
     * 获取附加属性3~10（冗余字段）
     *
     * @return add_atrrib3_10 - 附加属性3~10（冗余字段）
     */
    public String getAddAtrrib310() {
        return addAtrrib310;
    }

    /**
     * 设置附加属性3~10（冗余字段）
     *
     * @param addAtrrib310 附加属性3~10（冗余字段）
     */
    public void setAddAtrrib310(String addAtrrib310) {
        this.addAtrrib310 = addAtrrib310;
    }

    /**
     * @return disable
     */
    public String getDisable() {
        return disable;
    }

    /**
     * @param disable
     */
    public void setDisable(String disable) {
        this.disable = disable;
    }
}