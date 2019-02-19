package cn.xmp.generator.demo.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 返回信息枚举
 * </p>
 *
 * @author wangbowang
 * @since 2019/01/05
 */
@Getter
@AllArgsConstructor
public enum ReturnEnum {

    CODE_2011(2011, "message.user.info.not.exist"),
    CODE_2012(2012, "message.function.not.open.to.store.managers"),
    CODE_2013(2013, "message.unable.distinguish.current.user.distribute.channels"),
    CODE_2014(2014, "message.order.info.not.exist"),
    CODE_2015(2015, "message.order.have.distribute"),
    CODE_2016(2016, "message.order.update.failed"),
    CODE_2017(2017, "message.order.have.been.rejected"),
    CODE_2018(2018, "message.order.have.been.rejected.origin"),
    CODE_2019(2019, "message.order.have.finish"),
    CODE_2020(2020, "message.only.district.and.county.officials.have.the.power.of.assignment"),
    CODE_2021(2021, "message.user.order.role.are.not.valid"),
    CODE_2022(2022, "message.user.current.operation.inconsistent.with.the.user.of.the.order"),
    CODE_2023(2023, "message.distribute.user.info.not.exist"),
    CODE_2024(2024, "message.city.manager.info.not.exist"),
    CODE_2025(2025, "message.city.manager.do.not.have.distribute.user"),
    CODE_2026(2026, "message.city.manager.do.not.have.shop.assistant"),
    CODE_2027(2027, "message.order.time.out"),
    CODE_2028(2028, "message.org.query.org.info.failed"),
    CODE_2029(2029, "message.order.status.illegal"),
    CODE_2030(2030, "message.order.and.districuteUser.not.equals"),
    CODE_2031(2031, "message.visiting.staff.info.not.exist"),
    CODE_2032(2032, "message.only.visiting.staff.can..be.district"),
    CODE_2033(2033, "message.unable.to.get.user.type.permission.list"),
    CODE_2034(2034, "message.order.number.already.exists.and.cannot.be.added.again");

    private Integer code;
    private String value;

}



