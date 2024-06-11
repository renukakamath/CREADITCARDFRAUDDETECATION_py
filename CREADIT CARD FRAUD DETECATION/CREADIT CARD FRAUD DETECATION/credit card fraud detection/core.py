import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split

def vals(val):
	# Load the dataset from CSV file
	data = pd.read_csv('card.csv', header=None)

	# Split the dataset into training and testing sets
	X_train, X_test, y_train, y_test = train_test_split(data.drop(data.columns[-1], axis=1), data[data.columns[-1]], test_size=0.3, random_state=42)

	# Create a random forest classifier with 100 trees
	rf = RandomForestClassifier(n_estimators=100)

	# Train the classifier on the training set
	rf.fit(X_train, y_train)

	# Make predictions on the testing set
	predictions = rf.predict(X_test)

	# Evaluate the accuracy of the classifier
	accuracy = rf.score(X_test, y_test)
	print("Accuracy: ",accuracy)

	# vals=[[1,9.9753489,76.2861063,12,2]]
	predictions = rf.predict(val)
	print(predictions)

	return predictions
