package action;

import tool.neuralnetwork.BackProNetwork;
import tool.samplesprocess.SimpleSamples;

//×Ô¶¨ÒåÍøÂç
public class CustomAction {
	private int hidNum;
	private long trainNum;
	private double learningRate;
	private double momentum;
	private double desiredTrainAcc;
	private double desiredTrainMistake;

	public int getHidNum() {
		return hidNum;
	}
	public void setHidNum(int hidNum) {
		this.hidNum = hidNum;
	}
	public long getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(long trainNum) {
		this.trainNum = trainNum;
	}
	public double getLearningRate() {
		return learningRate;
	}
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	public double getMomentum() {
		return momentum;
	}
	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}
	public double getDesiredTrainAcc() {
		return desiredTrainAcc;
	}
	public void setDesiredTrainAcc(double desiredTrainAcc) {
		this.desiredTrainAcc = desiredTrainAcc;
	}
	public double getDesiredTrainMistake() {
		return desiredTrainMistake;
	}
	public void setDesiredTrainMistake(double desiredTrainMistake) {
		this.desiredTrainMistake = desiredTrainMistake;
	}
	
	public String execute() throws Exception{
		SimpleSamples trainSam = new SimpleSamples("E:\\Eclipse for JavaEE workplace\\BigDataDig\\data\\iris-train.txt", 1, 90, 4, true);
		SimpleSamples testSam = new SimpleSamples("E:\\Eclipse for JavaEE workplace\\BigDataDig\\data\\iris-test.txt", 1, 60, 4, true);
		BackProNetwork net = new BackProNetwork(4, hidNum, 2);
		net.setTrainNum(trainNum);
		net.setLearningRate(learningRate);
		net.setMomentum(momentum);
		net.setDesiredTrainAcc(desiredTrainAcc);
		net.setDesiredTrainMistake(desiredTrainMistake);
		net.train(trainSam, testSam, false);
		return "success";
	}

}
