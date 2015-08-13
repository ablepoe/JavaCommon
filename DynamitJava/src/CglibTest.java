import java.lang.reflect.Method;    
    import java.util.HashMap;    
        
    /**  
     * Cglib������  
     * @author cuiran  
     * @version 1.0  
     */    
    public class CglibTest {    
        @SuppressWarnings("unchecked")    
        public static void main(String[] args) throws ClassNotFoundException {    
        
            // �������Ա����    
            HashMap propertyMap = new HashMap();    
        
            for (int i = 0; i < 3; i++) {
            	propertyMap.put("_"+i, Class.forName("java.lang.Integer"));    
			}
            propertyMap.put("id", Class.forName("java.lang.Integer"));    
        
            propertyMap.put("name", Class.forName("java.lang.String"));    
        
            propertyMap.put("address", Class.forName("java.lang.String"));    
        
            // ���ɶ�̬ Bean    
            CglibBean bean = new CglibBean(propertyMap);    
        
            // �� Bean ����ֵ    
            bean.setValue("id", new Integer(123));    
        
            bean.setValue("name", "454");    
        
            bean.setValue("address", "789");    
        
            // �� Bean �л�ȡֵ����Ȼ�˻��ֵ�������� Object    
        
            System.out.println("  >> id      = " + bean.getValue("id"));    
        
            System.out.println("  >> name    = " + bean.getValue("name"));    
        
            System.out.println("  >> address = " + bean.getValue("address"));    
        
            // ���bean��ʵ��    
            Object object = bean.getObject();    
        
            // ͨ������鿴���з�����    
            Class clazz = object.getClass();    
            Method[] methods = clazz.getDeclaredMethods();    
            for (int i = 0; i < methods.length; i++) {    
                System.out.println(methods[i].getName());    
            }    
        }    
    }