

package com.choxsu.front.my.newsfeed;

import com.choxsu.common.entity.Remind;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * remind 提醒我业务，提醒用户去看消息
 * <p>
 * remind 表结构
 * accountId   int(11)    主键，对应到用户账号 id
 * referMe      int(11)    at 我的消息条数
 * message     int(11)    私信消息条数，暂时不用
 * @author choxsu
 */
public class RemindService {

    private final String remindCacheName = "remind";
    private Remind dao = new Remind().dao();

    private void createRemind(int accountId, int type) {
        Remind remind = dao.findById(accountId);
        boolean isExists = (remind != null);
        if (!isExists) {
            remind = new Remind();
            remind.setAccountId(accountId);
            remind.setReferMe(0);
            remind.setMessage(0);
            remind.setFans(0);
            // 如果有新的 remind 类型，这里需要添加 setXxx(0);
        }
        if (type == Remind.TYPE_REFER_ME) {
            remind.setReferMe(remind.getReferMe() + 1);
        } else if (type == Remind.TYPE_MESSAGE) {
            remind.setMessage(remind.getMessage() + 1);
        } else if (type == Remind.TYPE_FANS) {
            remind.setFans(remind.getFans() + 1);
        } else {
            throw new IllegalArgumentException("不支持的 type 值： " + type);
        }
        // 如果有新的 remind 类型，这里需要添加新的 if 分支进行加 1 操作

        if (isExists) {
            remind.update();
        } else {
            remind.save();
        }

        // 创建新记录后立即清掉缓存
        clearCache(accountId);
    }

    /**
     * 生成 @提到我 refer_me 的 remind 提醒
     *
     * @param accountId 提醒到的账户 id
     */
    public void createRemindOfReferMe(int accountId) {
        createRemind(accountId, Remind.TYPE_REFER_ME);
    }

    /**
     * 生成 私信 message 的 remind 提醒
     *
     * @param accountId 提醒到的账户 id
     */
    public void createRemindOfMessage(int accountId) {
        createRemind(accountId, Remind.TYPE_MESSAGE);
    }

    /**
     * 生成 粉丝增加时 friend 的 remind 提醒
     *
     * @param accountId 提醒到的账户 id
     */
    public void createRemindOfNewFans(int accountId) {
        createRemind(accountId, Remind.TYPE_FANS);
    }

    /**
     * 用户查看 @提到我 之后，重置 remind 中的 referMe 字段
     *
     * @param accountId 提醒到的账户 id
     */
    private void resetRemind(int accountId, int type) {
        Remind remind = getRemind(accountId);
        if (remind == null) {
            return;
        }

        if (type == Remind.TYPE_REFER_ME) {
            if (remind.getReferMe() != 0) {
                remind.setReferMe(0);
                remind.update();
            }
        } else if (type == Remind.TYPE_MESSAGE) {
            if (remind.getMessage() != 0) {
                remind.setMessage(0);
                remind.update();
            }
        } else if (type == Remind.TYPE_FANS) {
            if (remind.getFans() != 0) {
                remind.setFans(0);
                remind.update();
            }
        } else {
            throw new IllegalArgumentException("不支持的 type 值： " + type);
        }
    }

    /**
     * 用户查看 @提到我 之后，重置 remind 中的 referMe 字段
     *
     * @param accountId 提醒到的账户 id
     */
    public void resetRemindOfReferMe(int accountId) {
        resetRemind(accountId, Remind.TYPE_REFER_ME);
    }


    /**
     * 用户查看 私信 之后，重置 remind 中的 message 字段
     *
     * @param accountId 收到私信的账户 id
     */
    public void resetRemindOfMessage(int accountId) {
        resetRemind(accountId, Remind.TYPE_MESSAGE);
    }

    /**
     * 用户查看 粉丝 之后，重置 remind 中的 fans 字段
     *
     * @param accountId 增加粉丝的用户账户 id
     */
    public void resetRemindOfNewFans(int accountId) {
        resetRemind(accountId, Remind.TYPE_FANS);
    }

    /**
     * 为了避免每次都读库，引入缓存
     */
    public Remind getRemind(int accountId) {
        Remind remind = CacheKit.get(remindCacheName, accountId);
        if (remind == null) {
            remind = dao.findById(accountId);
            if (remind == null) {
                remind = new Remind();       // 创建一个临时对象放入缓存，避免每次读库
                remind.setAccountId(null);    // 临时对象的 accountId 设置为 null，便于后续做判断
            }
            CacheKit.put(remindCacheName, accountId, remind);
        }
        // 确保不是临时对象才返回对象，否则返回 null，临时对象的 accountId 为 null
        return remind.getAccountId() != null ? remind : null;
    }

    /**
     * 当用户的 remind 表发生变化时，必须清一下此 cache，否则数据不对
     */
    public void clearCache(int accountId) {
        CacheKit.remove(remindCacheName, accountId);
    }
}
