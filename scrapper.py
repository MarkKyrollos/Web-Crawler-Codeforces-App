import requests
from bs4 import BeautifulSoup
import mysql.connector
import re
import time


db = mysql.connector.connect(
    host="localhost",
    user="your_username",
    password="your_password",
    database="codeforces_db"
)
cursor = db.cursor()


BASE_URL = 'https://codeforces.com'


def parse_user_data(username):
    url = f"{BASE_URL}/profile/{username}"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')


    user_data = {
        "screen_name": username,
        "city": None,
        "country": None,
        "organization": None,
        "contributions": 0,
        "friends_count": 0,
        "registration_duration_days": 0,
        "problems_solved": 0,
        "streak_days": 0
    }
    return user_data

def parse_contest_data(contest_id):
    url = f"{BASE_URL}/contest/{contest_id}"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    

    contest_data = {
        "contest_name": None,
        "contest_date": None,
        "division": None,
        "writer": None,
        "problem_count": 0
    }
    return contest_data

def parse_problem_set(problem_id):
    url = f"{BASE_URL}/problemset/problem/{problem_id}"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    

    problem_data = {
        "problem_name": None,
        "tags": [],
        "time_limit": 0,
        "memory_limit": 0
    }
    return problem_data


def insert_user(user_data):
    cursor.execute("""
        INSERT INTO Users (screen_name, city, country, organization, contributions, friends_count, 
        registration_duration_days, problems_solved, streak_days)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
    """, (
        user_data['screen_name'], user_data['city'], user_data['country'], user_data['organization'],
        user_data['contributions'], user_data['friends_count'],
        user_data['registration_duration_days'], user_data['problems_solved'], user_data['streak_days']
    ))
    db.commit()

def insert_contest(contest_data):
    cursor.execute("""
        INSERT INTO Contests (contest_name, contest_date, division, writer, problem_count)
        VALUES (%s, %s, %s, %s, %s)
    """, (
        contest_data['contest_name'], contest_data['contest_date'], contest_data['division'], 
        contest_data['writer'], contest_data['problem_count']
    ))
    db.commit()

def insert_problem(problem_data):
    cursor.execute("""
        INSERT INTO Problem_Sets (problem_name, tags, time_limit, memory_limit)
        VALUES (%s, %s, %s, %s)
    """, (
        problem_data['problem_name'], ",".join(problem_data['tags']), problem_data['time_limit'], 
        problem_data['memory_limit']
    ))
    db.commit()

#main function
def main():
    usernames = ["user1", "user2"]
    contest_ids = [1000, 1001]
    problem_ids = [1, 2]


    for username in usernames:
        user_data = parse_user_data(username)
        insert_user(user_data)
        print(f"Inserted user: {username}")


    for contest_id in contest_ids:
        contest_data = parse_contest_data(contest_id)
        insert_contest(contest_data)
        print(f"Inserted contest ID: {contest_id}")


    for problem_id in problem_ids:
        problem_data = parse_problem_set(problem_id)
        insert_problem(problem_data)
        print(f"Inserted problem ID: {problem_id}")


    cursor.close()
    db.close()

if __name__ == "__main__":
    main()
