package matech.framework.autoCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import matech.utils.date.DateUtils;
import matech.utils.db.DBConnectPool;
import matech.utils.db.DbUtil;
import matech.utils.log.Log;




public class AutoCodeUtil
{
    private AutoCode uvoAutoCode;
    private int[] INT_SER_LENGTH;
    Log log;

    public AutoCodeUtil()
    {
        this.uvoAutoCode = null;

        this.INT_SER_LENGTH = new int[] { 0, 0, 0 };

        this.log = new Log(AutoCodeUtil.class);
    }

    private synchronized AutoCode peekCurrentSeries(String codeType, String codeOwner, Connection conn)
            throws Exception
    {
        if (codeType == null) {
            codeType = "";
        }

        if (codeOwner == null) {
            codeOwner = "";
        }

        if (codeType.equals("")) {
            throw new Exception("没有指定编号种类");
        }

        if (codeOwner.equals("")) {
            codeOwner = "all";
        }
        String sql = " SELECT * FROM MT_SYS_AUTO_CODE WHERE CODE_TYPE=?  AND CODE_OWNER=? for update";

        AutoCode ac = new AutoCode();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, codeType);
            ps.setString(2, codeOwner);

            rs = ps.executeQuery();

            if (rs.next())
            {
                this.log.debug(Integer.valueOf(rs.getInt("CUR_NUM1")));
                this.log.debug(Integer.valueOf(rs.getInt("SHOW_LEN1")));
                this.log.debug(rs.getString("CODE_FORMAT"));

                ac.setCodeId(rs.getString("CODE_ID"));
                ac.setCodeOwner(rs.getString("CODE_OWNER"));
                ac.setCodeType(rs.getString("CODE_TYPE"));
                ac.setCodeName(rs.getString("CODE_NAME"));
                ac.setInitNum(rs.getInt("INIT_NUM"));
                ac.setCurNum1(rs.getInt("CUR_NUM1"));
                ac.setCurNum2(rs.getInt("CUR_NUM2"));
                ac.setCurNum3(rs.getInt("CUR_NUM3"));
                ac.setShowLen1(rs.getInt("SHOW_LEN1"));
                ac.setShowLen2(rs.getInt("SHOW_LEN2"));
                ac.setShowLen3(rs.getInt("SHOW_LEN3"));
                ac.setCodeFormat(rs.getString("CODE_FORMAT"));
                ac.setCodeDes(rs.getString("CODE_DES"));
            }
        }
        catch (Exception dbe)
        {
        }
        finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
        }

        return ac;
    }

    public synchronized String getAutoCode(String codeType)
            throws Exception
    {
        return getAutoCode(codeType, "", null);
    }

    public synchronized String getAutoCode(String codeType, String codeOwner)
            throws Exception
    {
        return getAutoCode(codeType, codeOwner, null);
    }

    public synchronized String getAutoCode(String codeType, String codeOwner, String[] exts)
            throws Exception
    {
        Connection conn = null;
        String strResult = "";
        try
        {
            conn = DBConnectPool.getConnection();
            conn.setAutoCommit(false);

            if ((codeType == null) || (codeType.trim().equals(""))) {
                throw new Exception("没有指定编号种类");
            }
            if ((codeOwner == null) || (codeOwner.trim().equals("")))
            {
                codeOwner = "all";
            }

            AutoCode uvoAutoCode = peekCurrentSeries(codeType, codeOwner, conn);
            this.uvoAutoCode = uvoAutoCode;
            if (uvoAutoCode == null) {
                throw new Exception("还没有配置种类为[" + codeType + "],所有者为[" + codeOwner + "]的自动编号策略");
            }

            int[] ai = { -1, -1, -1 };
            ai[0] = uvoAutoCode.getCurNum1();
            ai[1] = uvoAutoCode.getCurNum2();
            ai[2] = uvoAutoCode.getCurNum3();

            int[] codeLen = { -1, -1, -1 };
            codeLen[0] = uvoAutoCode.getShowLen1();
            codeLen[1] = uvoAutoCode.getShowLen2();
            codeLen[2] = uvoAutoCode.getShowLen3();

            String strConf = uvoAutoCode.getCodeFormat();

            int oldIdx = 0;
            int idx = strConf.indexOf("${");
            while (idx >= 0) {
                String env = getEnv(strConf.substring(idx));
                strResult = strResult + strConf.substring(oldIdx, idx);
                if (env.equals("1"))
                {
                    ai[0] += 1;
                    strResult = strResult + ai[0];
                }
                else
                {
                    char fc;
                    if (env.startsWith("1F"))
                    {
                        ai[0] += 1;
                        fc = '0';
                        if (env.length() >= 3) {
                            fc = env.charAt(2);
                        }
                        strResult = strResult + fillChar(ai[0], codeLen[0], fc);
                    } else if (env.equals("2"))
                    {
                        ai[1] += 1;
                        strResult = strResult + ai[1];
                    } else if (env.startsWith("2F"))
                    {
                        ai[1] += 1;
                        fc = '0';
                        if (env.length() >= 3) {
                            fc = env.charAt(2);
                        }
                        strResult = strResult + fillChar(ai[1], codeLen[1], fc);
                    } else if (env.equals("3"))
                    {
                        ai[2] += 1;
                        strResult = strResult + ai[2];
                    } else if (env.startsWith("3F"))
                    {
                        ai[2] += 1;
                        fc = '0';
                        if (env.length() >= 3) {
                            fc = env.charAt(2);
                        }
                        strResult = strResult + fillChar(ai[2], codeLen[2], fc);
                    } else if (env.equals("OWNER"))
                    {
                        strResult = strResult + codeOwner;
                    } else if (env.equals("CURYEAR"))
                    {
                        String strDate = DateUtils.getCurrentYear();
                        strResult = strResult + strDate;
                    } else if (env.startsWith("EXT")) {
                        String extNum = "1";
                        if (env.length() > 3) {
                            extNum = env.substring(3);
                        }
                        int iExtNum = new Integer(extNum).intValue();
                        if ((exts == null) || (iExtNum > exts.length)) {
                            throw new Exception("自动编号配置错误,扩展信息长度非法[" + strConf + "]");
                        }
                        String strExt = exts[(iExtNum - 1)];
                        if (strExt == null) {
                            strExt = "";
                        }
                        strResult = strResult + strExt;
                    }
                }
                oldIdx = idx + 2 + env.length() + 1;
                idx = strConf.indexOf("${", oldIdx);
            }
            strResult = strResult + strConf.substring(oldIdx);

            setSeries(codeType, codeOwner, ai, conn);
            if (this.uvoAutoCode != null)
            {
                this.uvoAutoCode.setCurNum1(ai[0]);
                this.uvoAutoCode.setCurNum2(ai[1]);
                this.uvoAutoCode.setCurNum3(ai[2]);

                this.INT_SER_LENGTH[0] = uvoAutoCode.getShowLen1();
                this.INT_SER_LENGTH[1] = uvoAutoCode.getShowLen2();
                this.INT_SER_LENGTH[2] = uvoAutoCode.getShowLen3();
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
            this.log.log("生成自动编号【" + codeType + "】编号失败!");
        } finally {
            DbUtil.close(conn);
        }

        return strResult;
    }

    private synchronized String getEnv(String str)
    {
        int index = str.indexOf("}");
        return str.substring(2, index);
    }

    private synchronized void setSeries(String codeType, String codeOwner, int[] ai, Connection conn)
            throws Exception
    {
        if (codeType == null)
            codeType = "";
        if (codeOwner == null)
            codeOwner = "";
        if (ai == null)
            throw new Exception("没有指定值");
        if (ai.length <= 0)
            throw new Exception("没有指定值");
        if (codeType.trim().equals(""))
            throw new Exception("没有指定编号种类");
        if (codeOwner.trim().equals("")) {
            codeOwner = "all";
        }
        String sql = "update MT_SYS_AUTO_CODE set SHOW_LEN3=SHOW_LEN3 ";
        if (ai.length >= 1) {
            sql = sql + ", CUR_NUM1=" + ai[0];
        }
        if (ai.length >= 2) {
            sql = sql + ", CUR_NUM2=" + ai[1];
        }
        if (ai.length >= 3) {
            sql = sql + ", CUR_NUM3=" + ai[2];
        }
        if (ai.length >= 1) {
            if (codeOwner.equals("")) {
                codeOwner = "all";
            }

            sql = sql + " WHERE CODE_TYPE=? AND CODE_OWNER=? ";

            this.log.debug(sql);

            PreparedStatement ps = null;
            try
            {
                ps = conn.prepareStatement(sql);
                ps.setString(1, codeType);
                ps.setString(2, codeOwner);
                ps.execute();
            }
            catch (Exception e) {
                throw e;
            } finally {
                DbUtil.close(ps);
            }
        }
    }

    private synchronized String fillChar(int values, int len, char fchar, char aorb) throws Exception
    {
        String st_temp = "";
        int int_len = -1;
        st_temp = String.valueOf(values);
        int_len = st_temp.length();
        if (len < int_len)
            throw new Exception("编码已经超过了长度限制,请与系统管理员联系");
        for (int i = 1; i <= len - int_len; ++i) {
            if (aorb == 'a')
                st_temp = fchar + st_temp;
            else if (aorb == 'b') {
                st_temp = st_temp + fchar;
            }
        }

        return st_temp;
    }

    private synchronized String fillChar(int values, int len, char fchar) throws Exception {
        return fillChar(values, len, fchar, 'a');
    }
}
