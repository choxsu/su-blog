package com.choxsu.common.render;

import com.jfinal.captcha.Captcha;
import com.jfinal.captcha.CaptchaManager;
import com.jfinal.captcha.CaptchaRender;
import com.jfinal.kit.LogKit;
import com.jfinal.render.RenderException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author choxsu
 */
public class MyCaptchaRender extends CaptchaRender {

    /**
     * 生成验证码
     */
    @Override
    public void render() {
        Captcha captcha = createCaptcha();
        CaptchaManager.me().getCaptchaCache().put(captcha);

        Cookie cookie = new Cookie(captchaName, captcha.getKey());
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream sos = null;
        try {
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            drawGraphic(captcha.getValue(), image);

            sos = response.getOutputStream();
            ImageIO.write(image, "png", sos);
        } catch (IOException e) {
            if (getDevMode()) {
                throw new RenderException(e);
            }
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    LogKit.logNothing(e);
                }
            }
        }
    }

    @Override
    protected void drawGraphic(String randomString, BufferedImage image) {
        // 获取图形上下文
        Graphics2D g = image.createGraphics();
        //设置透明  start
       /* image = g.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
        g = image.createGraphics();
        Image from = image.getScaledInstance(image.getWidth(), image.getHeight(), BufferedImage.SCALE_AREA_AVERAGING);
        g.drawImage(from, 0, 0, null);*/
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 图形抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 字体抗锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 设定背景色
        g.setColor(getRandColor(120, 200));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //绘制小字符背景
        Color color = null;
        for (int i = 0; i < 20; i++) {
            color = getRandColor(120, 200);
            g.setColor(color);
            String rand = String.valueOf(charArray[random.nextInt(charArray.length)]);
            g.drawString(rand, random.nextInt(WIDTH), random.nextInt(HEIGHT));
            color = null;
        }

        //设定字体
        g.setFont(RANDOM_FONT[random.nextInt(RANDOM_FONT.length)]);
        // 绘制验证码
        for (int i = 0; i < randomString.length(); i++) {
            //旋转度数 最好小于45度
            int degree = random.nextInt(28);
            if (i % 2 == 0) {
                degree = degree * (-1);
            }
            //定义坐标
            int x = 22 * i, y = 21;
            //旋转区域
            g.rotate(Math.toRadians(degree), x, y);
            //设定字体颜色
            color = getRandColor(20, 130);
            g.setColor(color);
            //将认证码显示到图象中
            g.drawString(String.valueOf(randomString.charAt(i)), x + 8, y + 10);
            //旋转之后，必须旋转回来
            g.rotate(-Math.toRadians(degree), x, y);
        }
        //图片中间曲线，使用上面缓存的color
        g.setColor(color);
        //width是线宽,float型
        BasicStroke bs = new BasicStroke(3);
        g.setStroke(bs);
        //画出曲线
        QuadCurve2D.Double curve = new QuadCurve2D.Double(0d, random.nextInt(HEIGHT - 8) + 4, WIDTH / 2, HEIGHT / 2, WIDTH, random.nextInt(HEIGHT - 8) + 4);
        g.draw(curve);
        // 销毁图像
        g.dispose();
    }
}
