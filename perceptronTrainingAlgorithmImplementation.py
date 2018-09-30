/* Implementation of Perceptron Training Algorithm */

import random
import numpy as np
import matplotlib.pyplot as plt
w0 = random.uniform(-0.25, 0.25)
w1 = random.uniform(-1, 1)
w2 = random.uniform(-1, 1)
N = 1000
S = np.random.random((N*2, 2)) * 2 - 1
w = [[w0], [w1], [w2]]
print("weights for first case")
print(w)
S1 = []
S0 = []
desiredOutput = []
for i in range(1, N+1):
    desiredOutput.append(0)
for j in range(0, N):
    x = [[1, S[j][0], S[j][1]]]
    res = np.matmul(x, w)
    if res >= 0:
        S1.append([S[j][0], S[j][1]])
        desiredOutput.insert(j, 1)
    else:
        S0.append([S[j][0], S[j][1]])
        desiredOutput.insert(j, 0)
for k in range(0, len(S1)):
    plt.figure(1)
    plt.subplot(211)
    plt.plot(S1[k][0], S1[k][1], 'ro')
    print("S1 is as follows")
    print([S1[k][0], S1[k][1]])
for m in range(0, len(S0)):
    plt.plot(S0[m][0], S0[m][1], 'bo')

x1 = np.linspace(-1, 1, 100)
x2 = (-(w0/w2) - (w1/w2)*x1)
plt.plot(x1, x2, 'g--')
print("n=1")
n = 1
W0 = random.uniform(-1, 1)
W1 = random.uniform(-1, 1)
W2 = random.uniform(-1, 1)
W0_2 = W0
W1_2 = W1
W2_2 = W2
W0_3 = W0
W1_3 = W1
W2_3 = W2
W = [[W0], [W1], [W2]]
W_2 = [[W0_2], [W1_2], [W2_2]]
W_3 = [[W0_3], [W1_3], [W2_3]]
print("weight: ")
print(W)
epochNumber = 0
numberOfSuccessfulClassifies = 0
numberOfMisClassifications = 0
while numberOfSuccessfulClassifies != N:
    epochNumber = epochNumber + 1
    numberOfSuccessfulClassifies = 0
    numberOfMisClassifications = 0
    for l in range(0, N):
        x = [[1, S[l][0], S[l][1]]]
        res1 = np.matmul(x, W)
        if res1 >= 0:
            actualOutput = 1
        else:
            actualOutput = 0
        if actualOutput == desiredOutput[l]:
            numberOfSuccessfulClassifies = numberOfSuccessfulClassifies + 1
        else:
            if actualOutput < desiredOutput[l]:
                W0 = W0 + n*1
                W1 = W1 + n*S[l][0]
                W2 = W2 + n*S[l][1]
                W = [[W0], [W1], [W2]]
                numberOfMisClassifications = numberOfMisClassifications + 1
            else:
                W0 = W0 - n * 1
                W1 = W1 - n * S[l][0]
                W2 = W2 - n * S[l][1]
                W = [[W0], [W1], [W2]]
                numberOfMisClassifications = numberOfMisClassifications + 1
    print("epochNumber ")
    print(epochNumber)
    print("number of  misClassification ")
    print(numberOfMisClassifications)
    plt.subplot(212)
    plt.plot(epochNumber, numberOfMisClassifications, 'ro')
print("New set of weights")
print(W)
print("n=0.1")
n = 0.1
epochNumber = 0
numberOfSuccessfulClassifies = 0
numberOfMisClassifications = 0

while numberOfSuccessfulClassifies != N:
    epochNumber = epochNumber + 1
    numberOfSuccessfulClassifies = 0
    numberOfMisClassifications = 0
    for a in range(0, N):
        x = [[1, S[a][0], S[a][1]]]
        res1 = np.matmul(x, W_2)
        if res1 >= 0:
            actualOutput = 1
        else:
            actualOutput = 0
        if actualOutput == desiredOutput[a]:
            numberOfSuccessfulClassifies = numberOfSuccessfulClassifies + 1
        else:
            if actualOutput < desiredOutput[a]:
                W0_2 = W0_2 + n*1
                W1_2 = W1_2 + n*S[a][0]
                W2_2 = W2_2 + n*S[a][1]
                W_2 = [[W0_2], [W1_2], [W2_2]]
                numberOfMisClassifications = numberOfMisClassifications + 1
            else:
                W0_2 = W0_2 - n * 1
                W1_2 = W1_2 - n * S[a][0]
                W2_2 = W2_2 - n * S[a][1]
                W_2 = [[W0_2], [W1_2], [W2_2]]
                numberOfMisClassifications = numberOfMisClassifications + 1
    print("epochNumber ")
    print(epochNumber)
    print("number of  misClassification ")
    print(numberOfMisClassifications)
    plt.figure(2)
    plt.subplot(211)
    plt.plot(epochNumber, numberOfMisClassifications, 'ro')
print("New set of weights")
print(W_2)
print("n=10")
n = 10
epochNumber = 0
numberOfSuccessfulClassifies = 0
numberOfMisClassifications = 0
while numberOfSuccessfulClassifies != N:
    epochNumber = epochNumber + 1
    numberOfSuccessfulClassifies = 0
    numberOfMisClassifications = 0
    for b in range(0, N):
        x = [[1, S[b][0], S[b][1]]]
        res1 = np.matmul(x, W_3)
        if res1 >= 0:
            actualOutput = 1
        else:
            actualOutput = 0
        if actualOutput == desiredOutput[b]:
            numberOfSuccessfulClassifies = numberOfSuccessfulClassifies + 1
        else:
            if actualOutput < desiredOutput[b]:
                W0_3 = W0_3 + n*1
                W1_3 = W1_3 + n*S[b][0]
                W2_3 = W2_3 + n*S[b][1]
                W_3 = [[W0_3], [W1_3], [W2_3]]
                numberOfMisClassifications = numberOfMisClassifications + 1
            else:
                W0_3 = W0_3 - n * 1
                W1_3 = W1_3 - n * S[b][0]
                W2_3 = W2_3 - n * S[b][1]
                W_3 = [[W0_3], [W1_3], [W2_3]]
                numberOfMisClassifications = numberOfMisClassifications + 1
    print("epochNumber ")
    print(epochNumber)
    print("number of  misClassification ")
    print(numberOfMisClassifications)
    plt.subplot(212)
    plt.plot(epochNumber, numberOfMisClassifications, 'ro')
print("New set of weights")
print(W_3)
plt.show()
