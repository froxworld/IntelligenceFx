package Bonus;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CodeBonus {


    <T> T createObj(String nameclass) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {

        Class<T> clazz = (Class<T>) Class.forName(nameclass);

        // assumes the target class has a no-args Constructor
        return clazz.newInstance();
    }



    public class Os1 {

        String name = "test";

        public void test(String[] args)
        {
            try
            {
                Class classe = Class.forName("Os1");
                Os1 obj = (Os1) classe.newInstance();
                System.out.println(obj.name);
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

    }

    public static Object createObject(Constructor constructor,
                                      Object[] arguments) {

        System.out.println("Constructor: " + constructor.toString());
        Object object = null;

        try {
            object = constructor.newInstance(arguments);
            System.out.println("Object: " + object.toString());
            return object;
        } catch (InstantiationException e) {
            //handle it
        } catch (IllegalAccessException e) {
            //handle it
        } catch (IllegalArgumentException e) {
            //handle it
        } catch (InvocationTargetException e) {
            //handle it
        }
        return object;
    }

    /*
           scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getX();
            mousePosY = me.getY();
            mouseDeltaX = mousePosX - mouseOldX;
            mouseDeltaY = mousePosY - mouseOldY;
            if (me.isAltDown() && me.isShiftDown() && me.isPrimaryButtonDown()) {
                cam.rz.setAngle(cam.rz.getAngle() - mouseDeltaX);
            }
            else if (me.isAltDown() && me.isPrimaryButtonDown()) {
                cam.ry.setAngle(cam.ry.getAngle() - mouseDeltaX);
                cam.rx.setAngle(cam.rx.getAngle() + mouseDeltaY);
            }
            else if (me.isAltDown() && me.isSecondaryButtonDown()) {
                double scale = cam.s.getX();
                double newScale = scale + mouseDeltaX*0.01;
                cam.s.setX(newScale); cam.s.setY(newScale); cam.s.setZ(newScale);
            }
            else if (me.isAltDown() && me.isMiddleButtonDown()) {
                cam.t.setX(cam.t.getX() + mouseDeltaX);
                cam.t.setY(cam.t.getY() + mouseDeltaY);
            }
        }*/
}



