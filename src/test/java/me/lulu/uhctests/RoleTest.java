package me.lulu.uhctests;

import org.mcwonderland.uhc.api.enums.RoleName;

public class RoleTest {

    //    @Test
    public void test() {
        for (RoleName role : RoleName.values()) {
//            Assert.assertNotNull(SimpleRoleFactory.getRole(role));
        }


        int today = 15;
        int nextNeedToDo = 16;
        Integer finalDay = 17;

        boolean needToDo = today > nextNeedToDo && (finalDay != null) && today <= finalDay;

    }

}
