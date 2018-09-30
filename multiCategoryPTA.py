##This python code implemets the multi category Perceptron Training Algorithm for hand written digit classification using MNIST database

import struct as st
import numpy as np
import gzip
import matplotlib.pyplot as plt

filename = {'images': './train-images-idx3-ubyte.gz', 'labels': './train-labels-idx1-ubyte.gz'}

labels_array = np.array([])

data_types = {
        0x08: ('ubyte', 'B', 1),
        0x09: ('byte', 'b', 1),
        0x0B: ('>i2', 'h', 2),
        0x0C: ('>i4', 'i', 4),
        0x0D: ('>f4', 'f', 4),
        0x0E: ('>f8', 'd', 8)}

for name in filename.keys():
    if name == 'images':
        imagesfile = gzip.open(filename[name], 'r+')
    if name == 'labels':
        labelsfile = gzip.open(filename[name], 'r+')


imagesfile.seek(0)
magic = st.unpack('>4B', imagesfile.read(4))
if(magic[0] and magic[1])or(magic[2] not in data_types):
    raise ValueError("File Format not correct")


imagesfile.seek(4)
nImg = st.unpack('>I', imagesfile.read(4))[0]
nR = st.unpack('>I', imagesfile.read(4))[0]
nC = st.unpack('>I', imagesfile.read(4))[0]
nBytes = nImg*nR*nC
labelsfile.seek(8)
print("no. of images :: ", nImg)
print("no. of rows :: ", nR)
print("no. of columns :: ", nC)

images_array = 255 - np.asarray(st.unpack('>'+'B'*nBytes, imagesfile.read(nBytes))).reshape((nImg, nR, nC))
labels_array = np.asarray(st.unpack('>'+'B'*nImg, labelsfile.read(nImg))).reshape((nImg, 1))

print(labels_array)
print(labels_array.shape)
print(images_array.shape)

test_filename = {'images': './t10k-images-idx3-ubyte.gz', 'labels': './t10k-labels-idx1-ubyte.gz'}

test_labels_array = np.array([])

data_types = {
        0x08: ('ubyte', 'B', 1),
        0x09: ('byte', 'b', 1),
        0x0B: ('>i2', 'h', 2),
        0x0C: ('>i4', 'i', 4),
        0x0D: ('>f4', 'f', 4),
        0x0E: ('>f8', 'd', 8)}

for name in test_filename.keys():
    if name == 'images':
        test_imagesfile = gzip.open(test_filename[name], 'r+')
    if name == 'labels':
        test_labelsfile = gzip.open(test_filename[name], 'r+')


test_imagesfile.seek(0)
magic = st.unpack('>4B', test_imagesfile.read(4))
if(magic[0] and magic[1])or(magic[2] not in data_types):
    raise ValueError("File Format not correct")

test_imagesfile.seek(4)
nTestImg = st.unpack('>I', test_imagesfile.read(4))[0]
nTestR = st.unpack('>I', test_imagesfile.read(4))[0]
nTestC = st.unpack('>I', test_imagesfile.read(4))[0]
nTestBytes = nTestImg*nTestR*nTestC
test_labelsfile.seek(8)
print("no. of test images :: ", nTestImg)
print("no. of test rows :: ", nTestR)
print("no. of test columns :: ", nTestC)
test_images_array = 255 - np.asarray(st.unpack('>'+'B'*nTestBytes, test_imagesfile.read(nTestBytes)))\
    .reshape((nTestImg, nTestR, nTestC))
plt.figure()
plt.imshow(images_array[2], cmap=plt.get_cmap('gray'))
plt.title(labels_array[2])
plt.grid()


def u(x):
    if x >= 0:
        return 1
    else:
        return 0


W = np.random.uniform(-5, 5, 7840)
W = np.reshape(W, (10, 784))
W = np.matrix(W)
print(W)
eta = 1
eps = 0.2
n = 60000
epoch = 0

global output_vectors
output_vectors = np.array([1, 0, 0, 0, 0, 0, 0, 0, 0, 0], dtype=np.int)


def d(i): return np.roll(output_vectors, i)


errors = np.zeros(60000)
x = np.empty((784, n))
x = x.T
vu = np.vectorize(u)
training_misclassify = []
misclassify = 0
threshold = 1
while threshold == 1:
    for i in range(n):
        x[i] = np.ravel(images_array[i])
        v = np.dot(W, x[i])
        j = np.argmax(v)

        if j != labels_array[i]:
            errors[epoch] = errors[epoch] + 1
            misclassify = misclassify + 1
    training_misclassify.append(misclassify)
    print("Epoch: ", epoch)
    print("Error: ", errors)
    epoch = epoch + 1
    for i in range(n):
        W = W + np.dot(eps * (d(labels_array[i]) - vu(np.dot(W, x[i]))).T, np.array(x[i]).reshape((1, 784)))

    if errors[epoch - 1] / n <= eps:
        threshold = 0

print('Convergence Error: ', (errors[epoch - 1] / n))

x1_axis = np.linspace(0, epoch, epoch)
x2_axis = errors[0:epoch]
plt.figure(figsize=(9, 9))
plt.plot(x1_axis, x2_axis, "-", color="blue", label='test')
plt.xlabel('Epoch')
plt.ylabel('Number of Errors')

e = np.empty(len(training_misclassify) - 2)

for i in range(0, (len(training_misclassify) - 2)):
    e[i] = training_misclassify[i + 1] - training_misclassify[i]

e = np.insert(e, 0, training_misclassify[0])
e = np.append(e, 0)

j = 0
for j in range(0, epoch):
    print('Epoch:', j)
    print('Number of misclassifications:', e[j])

x1_axis = np.linspace(0, epoch, epoch)
x2_axis = e[0:epoch]
plt.figure(figsize=(9, 9))
plt.bar(x1_axis, x2_axis, color='black')
plt.xlabel('Epochs')
plt.ylabel('No. of misclassifications')


def test(W):
    misclass = 0
    n = 1000
    testX = np.empty((784, n))
    testX = testX.T
    for i in range(n):
        testX[i] = np.ravel(test_images_array[i])
        testV = np.dot(W, testX[i])
        testJ = np.argmax(testV)

        if testJ != labels_array[i]:
            misclass = misclass + 1
    return misclass


test_error = test(W)
print('Test Error: ', test_error)
plt.show()
