package com.kingston.game.crossrank;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.ServerConfig;

/**
 * provides a skeletal implementation of the <tt>CrossRank</tt>
 * @author kingston
 */
public abstract class AbstractCrossRank implements CrossRank {

	@Protobuf
	private int serverId;
	/**  record creating timestamp */
	@Protobuf
	private long createTime;
	@Protobuf
	private long playerId;
	/**  one level rank score*/
	@Protobuf
	private int score;
	/**  second level rank score */
	@Protobuf
	private int aid;

	/** 32位时间戳 */
	protected  long TIME_MAX_VALUE = 0xFFFFFFFFL; 

	public AbstractCrossRank(long playerId, int score, int aid) {
		this.playerId = playerId;
		this.score = score;
		this.aid  = aid;
		this.serverId = ServerConfig.getInstance().getServerId();
		this.createTime = System.currentTimeMillis();
	}

	public AbstractCrossRank(long playerId, int score) {
		this(playerId, score, 0);
	}
	
	public AbstractCrossRank() {
		
	}
	
	public int getRankType() {
		return  CrossRankKinds.FIGHTING;
	}
	
	public int getServerId() {
		return serverId;
	}
	
	public long getPlayerId() {
		return this.playerId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getScore() {
		return score;
	}

	public int getAid() {
		return aid;
	}
	
	@Override
	public String buildRankKey() {
		return "CrossRank_" + getRankType();
	}
	
	@Override
	public String buildResultKey() {
		return getClass().getSimpleName() ;
	}
	
	@Override
	public double buildRankScore() {
		//default rank score 
		// score      |     createtime
		//  20bits            32bits  
		long timePart = (TIME_MAX_VALUE - getCreateTime()/1000) & TIME_MAX_VALUE;
		long result  = (long)score << 32 | timePart;
//		System.err.println(( (long)score << 32)+"|"+timePart+"|"+result);
		return  result;
	}

	@Override
	public String toString() {
		return "AbstractCrossRank [serverId=" + serverId
						+ ", createTime=" + createTime
						+ ", playerId=" + playerId
						+ ", score=" + score + ", aid="
						+ aid + "]";
	}

}
